/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapviewer;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Kalle
 */
public class StringUtils {
    
    public static String Join(Collection<String> strings, String separator)
    {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iter = strings.iterator();
        
        while (iter.hasNext())
        {
            sb.append(iter.next()).append(iter.hasNext()?separator:"");
        }
        return sb.toString();
    }
    
    
}
