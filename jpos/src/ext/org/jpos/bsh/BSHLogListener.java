/*
 * Copyright (c) 2000 jPOS.org.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *    "This product includes software developed by the jPOS project 
 *    (http://www.jpos.org/)". Alternately, this acknowledgment may 
 *    appear in the software itself, if and wherever such third-party 
 *    acknowledgments normally appear.
 *
 * 4. The names "jPOS" and "jPOS.org" must not be used to endorse 
 *    or promote products derived from this software without prior 
 *    written permission. For written permission, please contact 
 *    license@jpos.org.
 *
 * 5. Products derived from this software may not be called "jPOS",
 *    nor may "jPOS" appear in their name, without prior written
 *    permission of the jPOS project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
 * IN NO EVENT SHALL THE JPOS PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR 
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS 
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING 
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the jPOS Project.  For more
 * information please see <http://www.jpos.org/>.
 */

package org.jpos.bsh;

import bsh.EvalError;
import bsh.Interpreter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Map;
import org.jpos.core.Configuration;
import org.jpos.util.LogEvent;

/** This is a log listener that reads its actions from Bean Shell scripts.
 * You can define many scripts, and the order in wich they are called, also you
 * can define scripts whose name depends on the event being processed, and the
 * realm of the object that generated it.
 * This way you can set a configuration like this:
 * <log-listener class="org.jpos.bsh.BSHLoglistener">
 *    <property name="source" value="general.bsh"/>
 *    <property name="source" value="tag_$tag.bsh"/> <!--this is to handle
 * specific tags-->
 *    <property name="source" value="realm_$realm.bsh"/> <!-- to handle specific
 * realms-->
 *    <property name="source" value="tag_$tag_realm_$realm.bsh"/> <!-- to handle
 * specific tags from specific realms-->
 * </log-listener>
 * <!-- the following lines are for html doc-->
 * <br>
 * <pre>
 * <font color="#008080">&lt;</font><font color="#008080">log-listener</font><font color="#008080"> </font><font color="#2e8b57"><b>class</b></font>=<font color="#ff00ff">&quot;org.jpos.bsh.BSHLoglistener&quot;</font><font color="#008080">&gt;</font>
 *       <font color="#008080">&lt;</font><font color="#008080">property</font><font color="#008080"> </font><font color="#2e8b57"><b>name</b></font>=<font color="#ff00ff">&quot;source&quot;</font><font color="#008080"> </font><font color="#2e8b57"><b>value</b></font>=<font color="#ff00ff">&quot;general.bsh&quot;</font><font color="#008080">/&gt;</font>
 *       <font color="#008080">&lt;</font><font color="#008080">property</font><font color="#008080"> </font><font color="#2e8b57"><b>name</b></font>=<font color="#ff00ff">&quot;source&quot;</font><font color="#008080"> </font><font color="#2e8b57"><b>value</b></font>=<font color="#ff00ff">&quot;tag_$tag.bsh&quot;</font><font color="#008080">/&gt;</font> <font color="#0000ff">&lt;!</font><font color="#0000ff">--this is to handle specific tags--</font><font color="#0000ff">&gt;</font>
 *       <font color="#008080">&lt;</font><font color="#008080">property</font><font color="#008080"> </font><font color="#2e8b57"><b>name</b></font>=<font color="#ff00ff">&quot;source&quot;</font><font color="#008080"> </font><font color="#2e8b57"><b>value</b></font>=<font color="#ff00ff">&quot;realm_$realm.bsh&quot;</font><font color="#008080">/&gt;</font> <font color="#0000ff">&lt;!</font><font color="#0000ff">-- to handle specific realms--</font><font color="#0000ff">&gt;</font>
 *       <font color="#008080">&lt;</font><font color="#008080">property</font><font color="#008080"> </font><font color="#2e8b57"><b>name</b></font>=<font color="#ff00ff">&quot;source&quot;</font><font color="#008080"> </font><font color="#2e8b57"><b>value</b></font>=<font color="#ff00ff">&quot;tag_$tag_realm_$realm.bsh&quot;</font><font color="#008080">/&gt;</font> <font color="#0000ff">&lt;!</font><font color="#0000ff">-- to handle specific tags from specific realms--</font><font color="#0000ff">&gt;</font>
 * <font color="#008080">&lt;/log-listener&gt;</font>
 * </pre>
 * <!-- end of lines for html doc-->
 * If a source with the given name is not found, or it canbe read, it is not processed, so this lets
 * you change what is processed in real time, if you put a file called
 * tag_SystemMonitor_realm_monitor.bsh it will be executed whenever the system
 * monitor is run.<BR>
 * If you want to filter an event so that the remaining log listeners doesn't see
 * it, you have to set event = null in your script.<br>
 * <table border=1 color="black">
 * <caption>Other Configuration Options: </caption>
 * <th><td> Name                </td><td>Type       </td><td>Description</td></th>
 * <tr><td> filter-by-default   </td><td>boolean    </td><td>
 *  If true, filter the events if no script is processed, this way you can put a
 * Log Listener that filters every thing unless you put some script file, even if
 * it is empty, you also can dynamically change what kind of message are filtered
 * by placing empty files with names like tag_SystemMonitor_realm_monitor.bsh in
 * the previous example, if you "touch" a file with this name these events will
 * begin to be processed.</TD></TR>
 * <TR><TD> preload-scripts     </TD><TD>boolean    </TD><TD>If true scripts a
 * loaded once, and kept in memory, being realoaded only if they are touched. This
 * is good when you have lots of RAM memory but ou have troubles with
 * speed</TD></TR>
 * </TABLE>
 */
public class BSHLogListener implements org.jpos.util.LogListener, org.jpos.core.Configurable {
    /**Holds the configuration for this object*/
    protected Configuration cfg;
    protected static final String[] patterns = {"tag", "realm"};
    protected Map scripts = new Hashtable();
    /** Creates a new instance of BSHLogListener */
    public BSHLogListener() {
    }
    
    public void setConfiguration(org.jpos.core.Configuration cfg) {
        this.cfg = cfg;
    }
    protected static String[] replace(String[] src, String[] patterns, String[] to){
        String[] ret = new String[src.length];
        for(int i=0; i<src.length; i++){
            StringBuffer buff = new StringBuffer(src[i]);
            for(int j=src[i].indexOf('$'); j>=0 && j<src[i].length()-1; j=src[i].indexOf('$',j)){
                String start = src[i].substring(j+1);
                boolean patternFound = false;
                for(int k=0; k<patterns.length && !patternFound ; k++){
                    if(patternFound = start.startsWith(patterns[k])) {
                        buff.replace(j, j + patterns[k].length() + 1, to[k]);
                        j+=patterns[k].length() + 1;
                    }
                }
                if(!patternFound) j++;
            }
            //if(buff.length()==0) ret[i] = src[i];
            ret[i] = buff.toString();
        }
        return ret;
    }
    public LogEvent log(org.jpos.util.LogEvent ev) {
        LogEvent ret = ev;
        boolean processed = false;
        try{
            String[] sources = replace(cfg.getAll("source"), patterns, new String[] {ev.tag, ev.getRealm()});
            for(int i=0; i<sources.length && ret != null; i++){
                try{
                    File f = new File(sources[i]);
                    if(f.exists() && f.canRead() && f.isFile()){
                        //if(f.lastModified())
                        processed = true;
                        Interpreter bsh = new Interpreter();
                        bsh.set("event", ret);
                        bsh.set("cfg", cfg);
                        if(!cfg.getBoolean("preload-scripts"))
                            bsh.eval(new java.io.FileReader(f));
                        else
                            eval(bsh, f, sources[i]);
                        ret = (LogEvent)bsh.get("event");
                    }else if(cfg.getBoolean("preload-scripts")) 
                        scripts.remove(sources[i]);
                }catch(Exception e){
                    ret.addMessage(e);
                }
            }
            if(!processed && cfg.getBoolean("filter-by-default")) return null;
            return ret;
        }catch(Exception e){
            ret.addMessage(e);
            return ret;
        }
    }
    protected String loadCode(File f) throws IOException{
        FileReader r = new FileReader(f);
        StringBuffer buf = new StringBuffer((int)f.length());
        char[] content = new char[(int)f.length()];
        int l;
        while((l=r.read(content))!=-1) buf.append(content,0,l);
        return buf.toString();
    }
    
    protected void eval(Interpreter bsh, File f, String source) throws EvalError, IOException{
        ScriptInfo info = (ScriptInfo)scripts.get(source);
        if(info == null) scripts.put(source, info=new ScriptInfo());
        String code;
        if(info.getLastModified() != f.lastModified()) {
            info.setLastModified(f.lastModified());
            info.setCode(loadCode(f));
        }
        bsh.eval(new StringReader(info.getCode()));
    }
    protected ScriptInfo getScriptInfo(String filename){
        return (ScriptInfo)scripts.get(filename);
    }
    protected void addScriptInfo(String filename, String code, long lastModified){
        scripts.put(filename, new ScriptInfo(code, lastModified));
    }
    protected class ScriptInfo{
        String code;
        long lastModified;
        public ScriptInfo(){
        }
        public ScriptInfo(String code, long lastModified){
            setCode(code);
            setLastModified(lastModified);
        }
        
        /** Getter for property code.
         * @return Value of property code.
         *
         */
        public java.lang.String getCode() {
            return code;
        }
        
        /** Setter for property code.
         * @param code New value of property code.
         *
         */
        public void setCode(java.lang.String code) {
            this.code = code;
        }
        
        /** Getter for property lastModified.
         * @return Value of property lastModified.
         *
         */
        public long getLastModified() {
            return lastModified;
        }
        
        /** Setter for property lastModified.
         * @param lastModified New value of property lastModified.
         *
         */
        public void setLastModified(long lastModified) {
            this.lastModified = lastModified;
        }
        
    }
}
