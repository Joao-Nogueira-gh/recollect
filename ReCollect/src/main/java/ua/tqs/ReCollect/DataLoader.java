package ua.tqs.ReCollect;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.CommentRepository;
import ua.tqs.ReCollect.repository.ItemRepository;
import ua.tqs.ReCollect.service.CommentService;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ItemRepository itemRepository;

    static final Logger logger = Logger.getLogger(DataLoader.class);

    @Autowired
    private ResourceLoader rl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //loading locations
        if (locationService.getAll().size() != 308) {
            locationService.deleteAll();
            String fileName = "cdpt.json";
            InputStream inputStream = rl.getResource("classpath:static/" + fileName).getInputStream();
            String cdpt = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, ArrayList<String>> map = mapper.readValue(cdpt, Map.class);

            Iterator<String> keys = map.keySet().iterator();
            while (keys.hasNext()) {
                String district = keys.next();
                ArrayList<String> counties = map.get(district);
                for (String county : counties) {
                    locationService.save(new Location(county, district));
                }
            }

            logger.info("Finished loading " + locationService.getAll().size() + " locations");

        } else {
            logger.debug("up and running");
        }
        // loading users
        String emailCarlos="carlos@mail.com";
        String emailSofia="sofia@mail.com";
        String emailPedro="pedro@mail.com";
        if (!userService.userExists(emailCarlos) || !userService.userExists(emailSofia) || !userService.userExists(emailPedro)) {
            userService.deleteAll();

            userService.register(new User("Carlos",emailCarlos, "carlos", "123456789",
                    locationService.getLocation("Aveiro", "Aveiro")));

            userService.register(new User("Sofia", emailSofia, "sofia", "123789456",
                    locationService.getLocation("Coimbra", "Mira")));

            userService.register(new User("Pedro", emailPedro, "pedro", "456789123",
                    locationService.getLocation("Lisboa", "Mafra")));
        }
        logger.debug(userService.getAll());

        //loading items

        itemService.deleteAll();

        User carlos=userService.getByEmail(emailCarlos);
        User sofia=userService.getByEmail(emailSofia);
        User pedro=userService.getByEmail(emailPedro);

        Item i1= new Item("2019 Great Britain Silver Britannia 1 oz .999 BU £2 Brilliant Uncirculated", 1, BigDecimal.valueOf(41.0), "Moeda rara", Categories.MISC);
        i1.setOwner(carlos);
        i1.addImage(new URL("https://images-na.ssl-images-amazon.com/images/I/81aA62TVZeL._SL1500_.jpg"));
        itemRepository.saveAndFlush(i1);

        Item i2= new Item("The Cocktail Party [assinado]", 1, BigDecimal.valueOf(200.0), "Livro antigo, assinado", Categories.BOOKS);
        i2.setOwner(carlos);
        i2.addImage(new URL("https://pictures.abebooks.com/LONDONRAREBOOKS/md/md30097028036.jpg"));
        itemRepository.saveAndFlush(i2);

        Item i3= new Item("Vintage Vinyl, Black Sabbath Volume 4. Vertigo Records 1972", 1, BigDecimal.valueOf(23.0), "Disco vinyl antigo", Categories.MUSIC);
        i3.setOwner(sofia);
        i3.addImage(new URL("https://i.ebayimg.com/images/g/TosAAOSw7Rdext5W/s-l1600.jpg"));
        itemRepository.saveAndFlush(i3);

        Item i4= new Item("Japanese Art Print Dusk by Koho Shoda Vintage Poster, Painting Print, Japanese, Ukiyo e #1098", 1, BigDecimal.valueOf(20.0), "Quadro raro", Categories.ART);
        i4.setSeller(sofia);
        i4.addImage(new URL("https://i.etsystatic.com/16900491/r/il/c479db/1938837110/il_1140xN.1938837110_au3l.jpg"));
        itemRepository.saveAndFlush(i4);

        Item i5= new Item("1922-1935 Peace Silver Dollar BU", 1, BigDecimal.valueOf(27.0), "Moeda rara", Categories.MISC);
        i5.setOwner(pedro);
        i5.addImage(new URL("https://www.images-apmex.com/images/Catalog%20Images/Products/170_Obv.jpg?v=20191107093552&width=900&height=900"));
        itemRepository.saveAndFlush(i5);

        Item i6= new Item("Complete Exodia the Forbidden One Set - All 5 Cards :: 1st Edition LDK2 Mint YuG", 2, BigDecimal.valueOf(17.0), "Cartas raras", Categories.GAMES);
        i6.setOwner(pedro);
        i6.addImage(new URL("https://i.ebayimg.com/images/g/sOEAAOSwKtVeVorv/s-l1600.jpg"));
        itemRepository.saveAndFlush(i6);

        logger.debug(itemService.getAll());

        //loading comments

        commentService.deleteAll();

        Comment c1=new Comment("Em bom estado!", carlos, i6);
        commentRepository.saveAndFlush(c1);
        
        Comment c2=new Comment("5 estrelas", pedro, i3);
        commentRepository.saveAndFlush(c2);
        
        Comment c3=new Comment("Tinha uma página rasgada...", pedro, i2);
        commentRepository.saveAndFlush(c3);

        logger.debug(commentService.getAll());

    }
}