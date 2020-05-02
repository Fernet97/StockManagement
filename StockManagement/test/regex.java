
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class regex {

    public static void main(String[] args) {

        String s = "1.0..1";
        char punto = '.';
        int x = 0;
        int index[];

//      for ( x=-1; (x = s.indexOf(punto, x + 1)) != -1; x++)
        while ((x = (s.indexOf(punto, x) + 1)) > 0) {
            System.out.println(x);
        }
    }
}
