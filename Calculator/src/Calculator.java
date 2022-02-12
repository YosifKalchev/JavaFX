import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


//todo Add second text field that shows numbers and actions inserted
//todo Resize main Frame and place additional text field in appropriate place

public class Calculator implements ActionListener {

    JFrame frame;
    JTextField mainTextField;
//    JTextField additionalTextField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton, negButton;
    JPanel panel;

    Font myFont = new Font("Ink Free", Font.BOLD, 30);

    double num1=0, num2=0, result=0;
    char operator;


    Calculator() {

        setupFrame();

        setupMainTextField();
//        setupAdditionalTextField();



        setupFunctionButtons();


        for (JButton functionButton : functionButtons) {
            functionButton.addActionListener(this);
            functionButton.setFont(myFont);
            functionButton.setFocusable(false);
        }

        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }
        negButton.setBounds(50, 430, 100,50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);


        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4,4,10,10));


        addButtonsToPanel();

        frame.add(panel);
        frame.add(negButton);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(mainTextField);
        frame.setVisible(true);
    }



    private void addButtonsToPanel() {
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);
    }


    private void setupFunctionButtons() {
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("C");
        negButton = new JButton("+/-");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;
    }

    public void addFunctionalityToOperatorButton(JButton button, char ch, ActionEvent e) {
        if (e.getSource() == button) {
            num1 = Double.parseDouble(mainTextField.getText());
            operator = ch;
            mainTextField.setText("");
        }
    }

    private void setupMainTextField() {
        mainTextField = new JTextField();
        mainTextField.setBounds(50, 25, 300, 50);
        mainTextField.setFont(myFont);
        mainTextField.setEditable(false);
    }


    //todo Create appropriate coordinates for the field
//    private void setupAdditionalTextField() {
//        additionalTextField = new JTextField();
//        additionalTextField.setText("additional");
//        additionalTextField.setBounds(50, 100, 100, 50);
//        additionalTextField.setFont(myFont);
//        additionalTextField.setEditable(false);
//    }

    private void setupFrame() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 650);
        frame.setLayout(null);
    }

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        setupNumberButtonsFunctionality(e);
        setupActionButtonsFunctionality(e);
    }

    private void setupActionButtonsFunctionality(ActionEvent e) {

        if (e.getSource() == decButton) {
            mainTextField.setText(mainTextField.getText().concat("."));
        }

        addFunctionalityToOperatorButton(addButton, '+', e);
        addFunctionalityToOperatorButton(subButton, '-', e);
        addFunctionalityToOperatorButton(mulButton, '*', e);
        addFunctionalityToOperatorButton(divButton, '/', e);

        if (e.getSource() == clrButton) {
            mainTextField.setText("");
        }

        if (e.getSource() == delButton) {
            String numberInserted = mainTextField.getText();
            mainTextField.setText("");
            for (int i = 0; i < numberInserted.length() -1; i++) {
                mainTextField.setText(mainTextField.getText()+numberInserted.charAt(i));
            }
        }

        if (e.getSource() == negButton) {
            double temp = Double.parseDouble(mainTextField.getText());
            temp *= -1;
            mainTextField.setText(String.valueOf(temp));
        }

        if (e.getSource() == equButton) {
            num2 = Double.parseDouble(mainTextField.getText());

            switch (operator) {
                case '+' -> result = num1 + num2;
                case '-' -> result = num1 - num2;
                case '*' -> result = num1 * num2;
                case '/' -> result = num1 / num2;
            }
            mainTextField.setText(String.valueOf(result));
            num1=result;
        }
    }

    private void setupNumberButtonsFunctionality(ActionEvent e) {
        for (int i = 0; i < numberButtons.length; i++) {
            if (e.getSource() == numberButtons[i]) {
                mainTextField.setText(mainTextField.getText().concat(String.valueOf(i)));
            }
        }
    }
}

//todo make the text field show the numbers and operators inserted!
