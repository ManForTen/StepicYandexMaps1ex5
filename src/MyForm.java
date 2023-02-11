import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyForm extends JFrame{

    private JPanel panel;
    private JLabel label;
    private JButton button1;
    private JButton button2;
    private JLabel image2;
    private final List<Place> places;
    private int index;

    MyForm() throws HeadlessException{
        super("Карта");
        places = new ArrayList<>();
        index = 0;
    }

    public void run() throws IOException, InterruptedException {
        addGui();
        createPlaces();
        setImageAndLabel(index);
        addButtonsActionListener();
        changeImage();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new MyForm().run();
    }

    public void changeImage() throws InterruptedException, IOException {
        while (true) {
            Thread.sleep(3000);
            setImageAndLabel(nextIndex());
        }
    }

    public void addGui() {
        add(panel);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void createPlaces() {
        places.add(new Place("Ялунвань Хайнань", "109.637462,18.228463&l=map&z=13"));
        places.add(new Place("Терракотовая армия", "109.253919,34.381302&l=sat,skl&z=15"));
        places.add(new Place("Запретный город", "116.391655,39.916416&l=map&z=15"));
        places.add(new Place("Горы Хуаншань", "118.329440,29.726002&l=sat,skl&z=12"));
        places.add(new Place("Дворец Потала", "91.117526,29.658007&l=sat,skl&z=17"));
    }

    public void addButtonsActionListener() {
        button1.addActionListener(e -> {
            try {
                setImageAndLabel(previousIndex());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button2.addActionListener(e -> {
            try {
                setImageAndLabel(nextIndex());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public int previousIndex() {
        if (index == 0) {
            index = places.size() - 1;
            return index;
        }
        return --index;
    }

    public int nextIndex() {
        if (index == places.size() - 1) {
            index = 0;
            return index;
        }
        return ++index;
    }

    public void setImageAndLabel(int index) throws IOException {
        label.setText(places.get(index).name);
        Image image = ImageIO.read(new URL("https://static-maps.yandex.ru/1.x/?ll=" + places.get(index).link));
        image2.setIcon(new ImageIcon(image));
    }

    static class Place {
        private final String name;
        private final String link;

        public Place(String name, String link) {
            this.name = name;
            this.link = link;
        }
    }
}

