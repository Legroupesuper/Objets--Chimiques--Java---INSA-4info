package foxesnrabbits;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class CustomCellRenderer extends DefaultTableCellRenderer 
{
    public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
        if( value instanceof String )
        {
            if (value.equals("f"))
            {
                cell.setBackground( Color.orange );
            }
            else if (value.equals("r"))
            {
                cell.setBackground( Color.cyan );
            }
            else{
            	cell.setBackground(Color.white);
            }
        }
        return cell;
    }
}