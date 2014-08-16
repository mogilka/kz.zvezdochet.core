package kz.zvezdochet.core.ui.comparator;

import java.text.Collator;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import kz.zvezdochet.core.util.DateUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * Сортировщик столбцов таблицы
 * @author Nataly
 */
public class TableSortListenerFactory implements Listener {
	@SuppressWarnings("rawtypes")
	private Comparator currentComparator = null;

	private Collator collator = Collator.getInstance(Locale.getDefault());
    
    public static final int INTEGER_COMPARATOR = 0;
    public static final int STRING_COMPARATOR = 1;
    public static final int DATE_COMPARATOR   = 2;
    public static final int DOUBLE_COMPARATOR = 3;
    public static final int TIME_COMPARATOR   = 4;
    
    private TableSortListenerFactory(int _comp) {
        switch (_comp) {
        case INTEGER_COMPARATOR: currentComparator = intComparator; break;
        case STRING_COMPARATOR: currentComparator = strComparator; break;
        case DATE_COMPARATOR: currentComparator = dateComparator; break;
        case DOUBLE_COMPARATOR: currentComparator = doubleComparator; break;
        case TIME_COMPARATOR: currentComparator = timeComparator; break;
        default: currentComparator = strComparator;
        }
    }
    
    public static Listener getListener(int _comp) {
        return new TableSortListenerFactory(_comp);
    }
    
    private int columnIndex = 0;
    private int updown   = 1;
          
	@SuppressWarnings("rawtypes")
	private Comparator intComparator = new Comparator() {
        public int compare(Object arg0, Object arg1) {
            TableItem t1 = (TableItem)arg0;
            TableItem t2 = (TableItem)arg1;

            int v1 = Integer.parseInt(t1.getText(columnIndex));
            int v2 = Integer.parseInt(t2.getText(columnIndex));
            
            if (v1<v2) return 1 * updown;
            if (v1>v2) return -1 * updown;
            return 0;
        }    
    };
         
	@SuppressWarnings("rawtypes")
	private Comparator strComparator = new Comparator() {
        public int compare(Object arg0, Object arg1) {
            TableItem t1 = (TableItem)arg0;
            TableItem t2 = (TableItem)arg1;

            String v1 = (t1.getText(columnIndex));
            String v2 = (t2.getText(columnIndex));

            return (collator.compare(v1,v2))*updown;
        }    
    };
    
	@SuppressWarnings("rawtypes")
	private Comparator doubleComparator = new Comparator() {
        public int compare(Object arg0, Object arg1) {
            TableItem t1 = (TableItem)arg0;
            TableItem t2 = (TableItem)arg1;
            
            double v1 = Double.parseDouble(t1.getText(columnIndex));
            double v2 = Double.parseDouble(t2.getText(columnIndex));
            
            if (v1<v2) return 1 * updown;
            if (v1>v2) return -1 * updown;
            return 0;
        }    
    };
    
	@SuppressWarnings("rawtypes")
    // Hour Comparator (hh:mm:ss)
	private Comparator timeComparator = new Comparator() {
        public int compare(Object arg0, Object arg1) {
            TableItem t1 = (TableItem)arg0;
            TableItem t2 = (TableItem)arg1;
            
            String v1 = (t1.getText(columnIndex)).trim();
            String v2 = (t2.getText(columnIndex)).trim();
            
            DateFormat df = DateUtil.stf;
            Date d1 = null; Date d2 = null;
            try {                
                d1 = df.parse(v1);
            } catch (ParseException e) { 
                System.out.println("[WARNING] v1 " + v1);
                try { d1 = df.parse("01:01:01"); } catch (ParseException e1) {}
            }
            try {               
                d2 = df.parse(v2);
            } catch (ParseException e) { 
                System.out.println("[WARNING] v2 " + v2);
                try { d2 = df.parse("01:01:01"); } catch (ParseException e1) {}
            }
            if (d1.equals(d2))
                return 0;
            return updown * (d1.before(d2) ? 1 : -1);
        }    
    };
    
	@SuppressWarnings("rawtypes")
	private Comparator dateComparator = new Comparator() {
        public int compare(Object arg0, Object arg1) {    
            TableItem t1 = (TableItem)arg0;
            TableItem t2 = (TableItem)arg1;
            
            String v1 = (t1.getText(columnIndex)).trim();
            String v2 = (t2.getText(columnIndex)).trim();

            //v1.replaceAll("-", "/");
            //v2.replaceAll("-", "/");
            
            DateFormat df = DateUtil.sdf;
            Date d1 = null; Date d2 = null;
            Calendar calendar = new GregorianCalendar(1900, 0, 1);
            try {
                d1 = df.parse(v1);
            } catch (ParseException e) { 
               //System.out.println("[WARNING] v1 " + v1);
                d1 = calendar.getTime();
            }
            try {               
                d2 = df.parse(v2);
            } catch (ParseException e) { 
                d2 = calendar.getTime();
            }
            if (d1.equals(d2))
                return 0;
            return updown * (d1.before(d2) ? 1 : -1);
        }    
    };
        
    @SuppressWarnings({ "unchecked" })
	public void handleEvent(Event e) {
        updown = (updown == 1 ? -1 : 1);
        
        TableColumn currentColumn = (TableColumn)e.widget;
        Table table = currentColumn.getParent();
        columnIndex = searchColumnIndex(currentColumn);
        
        table.setRedraw(false);
        TableItem[] items = table.getItems();
        Arrays.sort(items, currentComparator);
        table.setItemCount(items.length);
        
        for (int i = 0; i < items.length; i++) {   
            TableItem item = new TableItem(table,SWT.NONE,i);
            item.setText(getData(items[i]));
            items[i].dispose();
        }
        table.setRedraw(true);     
    }
    
    private String[] getData(TableItem t) {
        Table table = t.getParent();
        int colCount = table.getColumnCount();
        String [] s = new String[colCount];
        for (int i = 0;i<colCount;i++)
            s[i] = t.getText(i);
        return s;
    }
    
    private int searchColumnIndex(TableColumn currentColumn) {
        Table table = currentColumn.getParent();
        int in = 0;
        for (int i = 0;i<table.getColumnCount();i++)
            if (table.getColumn(i) == currentColumn)
                in = i;
        return in;
    }
}
