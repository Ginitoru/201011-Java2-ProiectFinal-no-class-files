package client.gui.frame;

import AppPackage.AnimationClass;
import client.gui.button.MinimizeButton;
import client.gui.label.MovingLabel;
import client.gui.label.pages.*;
import client.gui.panel.HorizontalTransparentPanel;
import client.util.focusAdapter.FocusAdapter;
import client.util.image.ImageTask;
import client.util.mouseAdaptors.MouseAdapterButton;
import client.util.mouseAdaptors.MouseAdapterLogAndRegister;
import client.util.mouseAdaptors.MouseAdapterMiniButton;
import client.util.notify.NotificationTask;
import client.util.sound.SoundConvertor;
import client.util.windowMovement.MoveFrameWithMouse;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private final int width = 1200;
    private final int height = 800;
    private JPanel mainPanel;
    private JLabel backgroundLabel;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private CreateClientAndVehiclePage clientAndVehiclePage;
    private PartPage partPage;
    private NotificationPage notificationPage;
    private AccountPage accountPage;
    private CreateOrderPage createOrderPage;
    private MovingLabel upperLabel, lowerLabel;
    private LeftButtonPage leftButtonPage;
    private HorizontalTransparentPanel upperPanel, lowerPanel;
    private MinimizeButton closeButton;
    private MinimizeButton minimizeButton;
    private NotificationTask notificationTask;
    private ImageTask imageTask;
    private JLabel loginLabel;
    private JLabel loginUserLabel;
    private MoveFrameWithMouse frameMove;


    private static AnimationClass slideEfect = new AnimationClass();
    private SoundConvertor soundConvertor;

    private List<JLabel> pages;

    private int poitX = 1800;
    private Color colorOrange = new Color(167,32,7);

    private MainFrame(){

        initFrame();
        initBackgroundLabel();
        soundConvertor = new SoundConvertor();
        initAccountPage();
        initLoginPage();
        initRegisterPage();
        initLeftButtonPage();
        initCreateClientAndVehiclePage();
        initPartPage();
        initNotificationPage();
        initCreateOrderPage();
        getPages();
        moveLAbelLeft();
        initUpperLabelAndPanel();
        initLowerLabelAndPanel();
        initCloseButton();
        initMinimizeButton();
        initNotificationTask();
        initImageTask();
        changeFocus();
        initFrameMove();
       // this.setVisible(true);
    }

    private void changeFocus(){
        this.addWindowFocusListener(new FocusAdapter(upperPanel, lowerPanel));
    }

    private void initFrameMove(){
        frameMove = new MoveFrameWithMouse(this);
    }

    private void initNotificationTask(){
        notificationTask = new NotificationTask(notificationPage);
    }

    private void initImageTask(){
        imageTask = new ImageTask(backgroundLabel);
    }

    private void initFrame(){
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        mainPanel = new JPanel();
        mainPanel.setSize(width,height);
        mainPanel.setLayout(null);
        this.add(mainPanel);
    }

    private void initBackgroundLabel(){
        backgroundLabel = new JLabel();
        backgroundLabel.setSize(width, height);
        mainPanel.add(backgroundLabel);
    }


    private void initLoginPage(){
        loginPage = new LoginPage(1200,0,1200,800);
        backgroundLabel.add(loginPage);

        loginPage.getLoginButton()
                .addMouseListener(new MouseAdapterButton(loginPage.getLoginButton()));

        loginPage.getRegisterButton()
                .addMouseListener(new MouseAdapterLogAndRegister());

        loginPage.getLoginButton()
                .addActionListener(ev -> login());

        loginPage.getRegisterButton()
                .addActionListener(ev -> moveLabesLeftAndRight(registerPage));
    }

    private void initRegisterPage(){
        registerPage = new RegisterPage(1200,0,1200,800);
        backgroundLabel.add(registerPage);

        registerPage.getRegisterButton()
                .addMouseListener(new MouseAdapterButton(registerPage.getRegisterButton()));

        registerPage.getLoginButton()
                .addMouseListener(new MouseAdapterLogAndRegister());

        registerPage.getLoginButton()
                .addActionListener(ev -> moveLabesLeftAndRight(loginPage));

        registerPage.getRegisterButton()
                .addActionListener(ev -> register());
    }

    private void initCloseButton(){
        closeButton = new MinimizeButton(1170,0,24,24, true);
        upperPanel.add(closeButton);
        closeButton.addMouseListener(new MouseAdapterMiniButton(closeButton, true));
        closeButton.addActionListener( ev -> closeProgram());
    }


    private void initMinimizeButton(){
        minimizeButton = new MinimizeButton(1144,0,24,24, false);
        upperPanel.add(minimizeButton);
        minimizeButton.addMouseListener(new MouseAdapterMiniButton(minimizeButton, false));
        minimizeButton.addActionListener( ev ->setExtendedState(JFrame.ICONIFIED));

    }

    private void initUpperLabelAndPanel(){
        upperLabel = new MovingLabel(0,0,1200,75);
        backgroundLabel.add(upperLabel);

        upperPanel = new HorizontalTransparentPanel(0,0,1200,75, true);
        upperPanel.setLayout(null);
        upperLabel.add(upperPanel);

        loginLabel = new JLabel("login:");
        loginLabel.setBounds(1000,30,30,20);
        loginLabel.setForeground(new Color(255,255,255,250));
        upperPanel.add(loginLabel);


        loginUserLabel = new JLabel("");
        loginUserLabel.setBounds(1035,30,50,20);
        loginUserLabel.setForeground(colorOrange);
        upperPanel.add(loginUserLabel);
    }

    private void initLowerLabelAndPanel(){
        lowerLabel = new MovingLabel(0,725,1200,75);
        backgroundLabel.add(lowerLabel);
        lowerPanel = new HorizontalTransparentPanel(0,0,1200,75, false);
        lowerLabel.add(lowerPanel);

    }

    private void initLeftButtonPage(){
        leftButtonPage = new LeftButtonPage(-200,75,200,650);
        backgroundLabel.add(leftButtonPage);

            leftButtonPage.getButtons().get(0)
                    .addActionListener( ev -> movePagesLeftAndRight(createOrderPage)); //create order button

            leftButtonPage.getButtons().get(1)
                    .addActionListener( ev -> movePagesLeftAndRight(clientAndVehiclePage)); //client and vehicle button

            leftButtonPage.getButtons().get(2)
                    .addActionListener(ev -> movePagesLeftAndRight(partPage)); //parts button

            leftButtonPage.getButtons().get(3)
                    .addActionListener( ev -> movePagesLeftAndRight(accountPage)); // my account page
    }


    private void initNotificationPage(){
        notificationPage = new NotificationPage(1200,680,250,120);
        backgroundLabel.add(notificationPage);
    }

    private void initCreateClientAndVehiclePage(){
        clientAndVehiclePage = new CreateClientAndVehiclePage(poitX,0,1200,800);
        backgroundLabel.add(clientAndVehiclePage);

        clientAndVehiclePage.getFindClientButton()
                .addActionListener( ev -> clientAndVehiclePage.findClient());

        clientAndVehiclePage.getCreateClientButton()
                .addActionListener(ev -> clientAndVehiclePage.createClient());

        clientAndVehiclePage.getCreateVehicleButton()
                .addActionListener(ev -> clientAndVehiclePage.createVehicle());
    }

    private void initPartPage(){
        partPage = new PartPage(poitX,0,1200,800);
        backgroundLabel.add(partPage);

        partPage.getCreatePartButton()
                .addActionListener(ev ->partPage.createPart());

        partPage.getRefreshOrdersButton()
                .addActionListener(ev -> partPage.refreshOrdersTable());

        partPage.getClosePartOrder()
                .addActionListener(ev -> partPage.closePartOrder());

    }

    private void initAccountPage(){
        accountPage = new AccountPage(poitX,0,1200,800);
        backgroundLabel.add(accountPage);
    }

    private void initCreateOrderPage(){
        createOrderPage = new CreateOrderPage(poitX,0,1200,800);
        backgroundLabel.add(createOrderPage);

        createOrderPage.getCreateOrderButton()
                .addActionListener(ev -> createOrderPage.createOrder());

        createOrderPage.getFindCarButton()
                .addActionListener(ev -> createOrderPage.findCar());

        createOrderPage.getBillButton()
                .addActionListener(ev -> createOrderPage.makeBill());

        createOrderPage.getAddProblemButton()
                .addActionListener(ev -> createOrderPage.addCarProblems());

        createOrderPage.getRefreshOrdersButton()
                .addActionListener(ev -> createOrderPage.refreshOrderTable2());
    }


    private void login(){
        if(loginPage.validCredentials()){
            moveLoginPageRaightAndLeftButtonPageRight();
        }
    }

    private void register(){
        registerPage.addUser();
    }

    private List<JLabel> getPages(){
        pages = new ArrayList<>();
        pages.add(createOrderPage);
        pages.add(clientAndVehiclePage);
        pages.add(partPage);
        pages.add(accountPage);
        pages.add(loginPage);
        pages.add(registerPage);
        return pages;
    }

    private void moveLabesLeftAndRight(JLabel labelPage){
        for(JLabel page: pages){
            if((page.getX() == 400)  && (page != labelPage)){
                slideEfect.jLabelXRight(400,1200,1,4,page);
                slideEfect.jLabelXLeft(1200,400,1,4,labelPage);
            }
        }
    }

    private void movePagesLeftAndRight(JLabel labelPage){

        for(JLabel page : pages){
            if(page.getX() == 0 && (page != labelPage)){
                slideEfect.jLabelXRight(0,poitX,1,6,page);
                slideEfect.jLabelXLeft(poitX,0,1,6,labelPage);
            }
        }
    }

    private void moveLoginPageRaightAndLeftButtonPageRight(){
        slideEfect.jLabelXRight(-200,0,2, 4,leftButtonPage);
        slideEfect.jLabelXRight(400,1200,2,4, loginPage);
        slideEfect.jLabelXLeft(poitX,0,1,6,createOrderPage);
    }

    private void moveLAbelLeft(){
        slideEfect.jLabelXLeft(1200,400,2,4,loginPage);
    }


    private void closeProgram(){

        try{
            this.dispose();
        }finally {
            notificationTask.getNotifyExecutor().shutdown();
            imageTask.getRandomPicture().shutdown();
        }
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

    public void setRegisterPage(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }

    public PartPage getPartPage() {
        return partPage;
    }

    public void setPartPage(PartPage partPage) {
        this.partPage = partPage;
    }

    public AccountPage getAccountPage() {
        return accountPage;
    }

    public void setAccountPage(AccountPage accountPage) {
        this.accountPage = accountPage;
    }

    public CreateOrderPage getCreateOrderPage() {
        return createOrderPage;
    }

    public void setCreateOrderPage(CreateOrderPage createOrderPage) {
        this.createOrderPage = createOrderPage;
    }


    private static final class SingletonHolder{
        public static final MainFrame INSTANCE = new MainFrame();
    }

    public NotificationTask getNotificationTask() {
        return notificationTask;
    }

    public void setNotificationTask(NotificationTask notificationTask) {
        this.notificationTask = notificationTask;
    }

    public static MainFrame getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void setLoginUserLabel(JLabel loginUserLabel) {
        this.loginUserLabel = loginUserLabel;
    }

    public JLabel getLoginUserLabel() {
        return loginUserLabel;
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }
}
