import org.mariuszgromada.math.mxparser.Expression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Form {
    private JPanel mainPanel;
    private JTextArea historyTextArea;
    private JTextField formulaInput;
    private JButton evalButton;
    private JList functionsList;
    private JScrollPane scrollCointainerPane;
    private Double lastResult=0.;
    private String lastEntry="";
    public Form(){
        JFrame frame = new JFrame("Calculator");

        JMenuBar mb=new JMenuBar();
        frame.setJMenuBar(mb);

        JMenu menu = new JMenu("Options");
        JMenuItem i1 =new JMenuItem("Reset");
        JMenuItem i2 =new JMenuItem("Exit");
        menu.add(i1); menu.add(i2);
        mb.add(menu);

        frame.setContentPane( this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(800,600);
        frame.setMinimumSize(new Dimension(600, 400));

        MathOperations blank=new MathOperations("- - - - - - - - - - - - - -","");

        MathOperations sin=new MathOperations("Sinus","sin()");
        MathOperations cos=new MathOperations("Cosinus","cos()");
        MathOperations tan=new MathOperations("Tangens","tan()");
        MathOperations ctg=new MathOperations("Cotangens","ctan()");
        MathOperations sqrt=new MathOperations("Square root","sqrt()");

        MathOperations addi=new MathOperations("Addition","+");
        MathOperations subs=new MathOperations("Substraction","-");
        MathOperations mult=new MathOperations("Multiplication","*");

        MathOperations pi=new MathOperations("PI Value","pi");
        MathOperations e=new MathOperations("E number","e");
        MathOperations phi=new MathOperations("Phi number","[phi]");

        MathOperations last=new MathOperations("Last Result","");


        DefaultListModel<MathOperations> listModel=new DefaultListModel<>();
        listModel.addElement(sin);
        listModel.addElement(cos);
        listModel.addElement(tan);
        listModel.addElement(ctg);
        listModel.addElement(sqrt);

        listModel.addElement(blank);

        listModel.addElement(addi);
        listModel.addElement(subs);
        listModel.addElement(mult);

        listModel.addElement(blank);

        listModel.addElement(pi);
        listModel.addElement(e);
        listModel.addElement(phi);

        listModel.addElement(blank);

        listModel.addElement(last);

        functionsList.setModel(listModel);

        //FormulaExecution
        evalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Expression expression = new Expression(formulaInput.getText());

                if (expression.checkSyntax()) {
                    Double result = expression.calculate();
                    historyTextArea.append(formulaInput.getText()+"="+result+"\n\n");
                    lastResult=result;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Wrong Input!\n" , "Calculator ERROR", JOptionPane.ERROR_MESSAGE);
                }
                lastEntry=formulaInput.getText();
                formulaInput.setText("");
            }
        });

        formulaInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                //Arrow up - previous entry
                if(e.getKeyCode() == 38) {
                    formulaInput.setText(lastEntry);
                }

                //Enter as doubleclick
                if(e.getKeyCode() == 10) {
                    Expression expression = new Expression(formulaInput.getText());
                    if (expression.checkSyntax()) {
                        Double result = expression.calculate();
                        historyTextArea.append(formulaInput.getText()+"="+result+"\n\n");
                        lastResult=result;
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Wrong Input!\n" , "Calculator ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    lastEntry=formulaInput.getText();
                    formulaInput.setText("");
                }
            }
        });
        //List DoubleClick
        functionsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList klik=(JList)e.getSource();
                if (e.getClickCount() == 2) {
                    int index = klik.locationToIndex(e.getPoint());
                    if(klik.getSelectedValue().toString().equals("Last Result")!=true)
                    {
                        MathOperations m=(MathOperations)klik.getSelectedValue();
                        formulaInput.setText(m.getShortName());

                        if(m.getShortName().contains("()"))
                        formulaInput.setCaretPosition(m.getShortName().length()-1);

                    }
                    else
                        formulaInput.setText(lastResult.toString());

                    formulaInput.requestFocus();
                    lastEntry=formulaInput.getText();
                }
            }
        });

        //Exit in menubar
        class ExitAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
        i2.addActionListener(new ExitAction());

        //Reset in menubar
        class ResetAction implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                formulaInput.setText(null);
                historyTextArea.setText(null);
            }
        }
        i1.addActionListener(new ResetAction());
    }
}
