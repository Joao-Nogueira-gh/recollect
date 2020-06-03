package ua.tqs.ReCollect;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    private static final String DBTIMEZONE = "Europe/London";

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
            @SuppressWarnings("unchecked")
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
        String emailMiguel="miguel@mail.com";
        String emailAna="ana@mail.com";

        if (!userService.userExists(emailCarlos) || !userService.userExists(emailSofia) || !userService.userExists(emailPedro) || !userService.userExists(emailMiguel) || !userService.userExists(emailAna)) {

            userService.register(new User("Carlos",emailCarlos, "carlos", "123456789",
                    locationService.getLocation("Aveiro", "Aveiro")));

            userService.register(new User("Sofia", emailSofia, "sofia", "123789456",
                    locationService.getLocation("Coimbra", "Mira")));

            userService.register(new User("Pedro", emailPedro, "pedro", "456789123",
                    locationService.getLocation("Lisboa", "Mafra")));

            userService.register(new User("Miguel", emailMiguel, "miguel", "234567891",
                    locationService.getLocation("Faro", "Albufeira")));

            userService.register(new User("Ana", emailAna, "ana", "789123456",
                    locationService.getLocation("Porto", "Maia")));
        }
        
        logger.debug("Finished loading "+userService.getAll().size()+" users.");

        //loading items

        itemService.deleteAll();

        User carlos=userService.getByEmail(emailCarlos);
        User sofia=userService.getByEmail(emailSofia);
        User pedro=userService.getByEmail(emailPedro);
        User miguel=userService.getByEmail(emailMiguel);
        User ana=userService.getByEmail(emailAna);

        Item i1= new Item("2019 Great Britain Silver Britannia 1 oz .999 BU £2 Brilliant Uncirculated", 1, BigDecimal.valueOf(41.0), "Moeda rara", Categories.MISC);
        i1.setOwner(carlos);
        i1.addImage(new URL("https://images-na.ssl-images-amazon.com/images/I/81aA62TVZeL._SL1500_.jpg"));
        itemRepository.saveAndFlush(i1);
        i1.setCreationDate(Date.from(LocalDate.of(2020, 6, 1).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));


        Item i2= new Item("The Cocktail Party [assinado]", 1, BigDecimal.valueOf(200.0), "Livro antigo, assinado", Categories.BOOKS);
        i2.setOwner(carlos);
        i2.addImage(new URL("https://pictures.abebooks.com/LONDONRAREBOOKS/md/md30097028036.jpg"));
        itemRepository.saveAndFlush(i2);
        i2.setCreationDate(Date.from(LocalDate.of(2020, 5, 23).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));


        Item i3= new Item("Vintage Vinyl, Black Sabbath Volume 4. Vertigo Records 1972", 1, BigDecimal.valueOf(23.0), "Disco vinyl antigo", Categories.MUSIC);
        i3.setOwner(sofia);
        i3.addImage(new URL("https://i.ebayimg.com/images/g/TosAAOSw7Rdext5W/s-l1600.jpg"));
        itemRepository.saveAndFlush(i3);
        i3.setCreationDate(Date.from(LocalDate.of(2020, 4, 2).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));


        Item i4= new Item("Japanese Art Print Dusk by Koho Shoda Vintage Poster, Painting Print, Japanese, Ukiyo e #1098", 1, BigDecimal.valueOf(20.0), "Quadro raro", Categories.ART);
        i4.setSeller(sofia);
        i4.addImage(new URL("https://i.etsystatic.com/16900491/r/il/c479db/1938837110/il_1140xN.1938837110_au3l.jpg"));
        itemRepository.saveAndFlush(i4);
        i4.setCreationDate(Date.from(LocalDate.of(2020, 5, 23).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));


        Item i5= new Item("1922-1935 Peace Silver Dollar BU", 1, BigDecimal.valueOf(27.0), "Moeda rara", Categories.MISC);
        i5.setOwner(pedro);
        i5.addImage(new URL("https://www.images-apmex.com/images/Catalog%20Images/Products/170_Obv.jpg?v=20191107093552&width=900&height=900"));
        itemRepository.saveAndFlush(i5);
        i5.setCreationDate(Date.from(LocalDate.of(2020, 5, 4).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));


        Item i6= new Item("Complete Exodia the Forbidden One Set - All 5 Cards :: 1st Edition LDK2 Mint YuG", 2, BigDecimal.valueOf(17.0), "Cartas raras", Categories.GAMES);
        i6.setOwner(pedro);
        i6.addImage(new URL("https://i.ebayimg.com/images/g/sOEAAOSwKtVeVorv/s-l1600.jpg"));
        itemRepository.saveAndFlush(i6);
        i6.setCreationDate(Date.from(LocalDate.of(2020, 4, 1).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));


        Item i7= new Item("1907 $10 Indian Gold MS-62 Quality", 1, BigDecimal.valueOf(1600.0), "Moeda muito rara", Categories.MISC);
        i7.setOwner(carlos);
        i7.addImage(new URL("https://www.rarecoins.com/media/catalog/product/cache/6/image/500x/040ec09b1e35df139433887a97daa66f/1/9/1907-10-indian-ms-62-holder.png"));
        itemRepository.saveAndFlush(i7);
        i7.setCreationDate(Date.from(LocalDate.of(2020, 4, 22).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i8= new Item("Outlanders, signed", 1, BigDecimal.valueOf(30.0), "Signed book", Categories.BOOKS);
        i8.setOwner(sofia);
        i8.addImage(new URL("https://pictures.abebooks.com/BETWEENTHECOVERS/47779555.jpg"));
        itemRepository.saveAndFlush(i8);
        i8.setCreationDate(Date.from(LocalDate.of(2020, 5, 1).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i9= new Item("BATMAN FOREVER: THE ARCADE GAME  (PlayStation)", 1, BigDecimal.valueOf(135.0), "Rare game", Categories.GAMES);
        i9.setOwner(pedro);
        i9.addImage(new URL("https://now.estarland.com/images/products/hr/6190/021481870838.jpg"));
        itemRepository.saveAndFlush(i9);
        i9.setCreationDate(Date.from(LocalDate.of(2020, 5, 3).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i10= new Item("CMC 1/18 Ferrari F2 500 Super rare / very Limited 1/18 CMC / Diecast cars 1/18 / CMC products", 1, BigDecimal.valueOf(430.0), "Rare toy", Categories.TOYS);
        i10.setOwner(miguel);
        i10.addImage(new URL("https://i.etsystatic.com/17352431/r/il/82a857/2379398821/il_794xN.2379398821_ey5n.jpg"));
        itemRepository.saveAndFlush(i10);
        i10.setCreationDate(Date.from(LocalDate.of(2020, 5, 20).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i11= new Item("Apple IIc Plus", 1, BigDecimal.valueOf(440.0), "Rare computer", Categories.TECHNOLOGY);
        i11.setOwner(miguel);
        i11.addImage(new URL("https://i.ebayimg.com/images/g/Hi0AAOSw0uZezTju/s-l1600.jpg"));
        itemRepository.saveAndFlush(i11);
        i11.setCreationDate(Date.from(LocalDate.of(2020, 5, 22).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i12= new Item("Eric Clapton - I Still Do", 1, BigDecimal.valueOf(135.0), "Rare disc", Categories.MUSIC);
        i12.setOwner(miguel);
        i12.addImage(new URL("https://www.vinyltap.co.uk/media/catalog/product/cache/1/image/364x/040ec09b1e35df139433887a97daa66f/i/m/image_236192_3_1_3_5_8_6_6_10_1_266708_1_118752_1_9_1_339533_1_36236_1_106114.jpg"));
        itemRepository.saveAndFlush(i12);
        i12.setCreationDate(Date.from(LocalDate.of(2020, 5, 29).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i13= new Item("Black Labrador Rare Painting by Ivester Lloyd", 1, BigDecimal.valueOf(2269.0), "Rare painting", Categories.ART);
        i13.setOwner(ana);
        i13.addImage(new URL("https://a.1stdibscdn.com/a_12252/1558701829687/Labrador_master.jpg?disable=upscale&auto=webp&quality=60&width=960"));
        itemRepository.saveAndFlush(i13);
        i13.setCreationDate(Date.from(LocalDate.of(2020, 3, 2).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));
        
        Item i14= new Item("1970s Tonka Truck", 1, BigDecimal.valueOf(93.0), "Rare toy", Categories.TOYS);
        i14.setOwner(ana);
        i14.addImage(new URL("https://i.etsystatic.com/23439917/r/il/0194b3/2384136403/il_794xN.2384136403_mrwj.jpg"));
        itemRepository.saveAndFlush(i14);
        i14.setCreationDate(Date.from(LocalDate.of(2020, 3, 4).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i15= new Item("Futurama-PlayStation2", 1, BigDecimal.valueOf(152.0), "Rare game", Categories.GAMES);
        i15.setOwner(ana);
        i15.addImage(new URL("https://images.lukiegames.com/t_300e2/assets/images/PS2/ps2_futurama-110214.jpg"));
        itemRepository.saveAndFlush(i15);
        i15.setCreationDate(Date.from(LocalDate.of(2020, 3, 30).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i16= new Item("Space for Sound - Marilyn Francis", 1, BigDecimal.valueOf(35.0), "First edition", Categories.BOOKS);
        i16.setOwner(pedro);
        i16.addImage(new URL("https://pictures.abebooks.com/MBHR/md/md30469129365.jpg"));
        itemRepository.saveAndFlush(i16);
        i16.setCreationDate(Date.from(LocalDate.of(2020, 5, 24).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i17= new Item("The Dogged Victims of Dan Jenkins- first edition", 1, BigDecimal.valueOf(227.0), "Livro antigo, primeira edicao", Categories.BOOKS);
        i17.setOwner(ana);
        i17.addImage(new URL("https://pictures.abebooks.com/FINEGOLFBOOKS/md/md22151098148.jpg"));
        itemRepository.saveAndFlush(i17);
        i17.setCreationDate(Date.from(LocalDate.of(2020, 5, 27).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i18= new Item("Chimp and Chump by Ruth Carroll", 1, BigDecimal.valueOf(100.0), "1ºedicao", Categories.BOOKS);
        i18.setOwner(miguel);
        i18.addImage(new URL("https://pictures.abebooks.com/USEDMOE/md/md30433021705.jpg"));
        itemRepository.saveAndFlush(i18);
        i18.setCreationDate(Date.from(LocalDate.of(2020, 5, 29).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i19= new Item("The Song of the Frog-Rodger Taylor, first edition!", 1, BigDecimal.valueOf(25.0), "Livro primeira edition", Categories.BOOKS);
        i19.setOwner(carlos);
        i19.addImage(new URL("https://pictures.abebooks.com/MBHR/md/md16923818723.jpg"));
        itemRepository.saveAndFlush(i19);
        i19.setCreationDate(Date.from(LocalDate.of(2020, 6, 1).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));
        //bruh
        Item i20= new Item("The Evidence of Love by Jacobson, Dan", 1, BigDecimal.valueOf(38.0), "old signed book", Categories.BOOKS);
        i20.setOwner(sofia);
        i20.addImage(new URL("https://pictures.abebooks.com/TIMKC/md/md533348201.jpg"));
        itemRepository.saveAndFlush(i20);
        i20.setCreationDate(Date.from(LocalDate.of(2020, 6, 2).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i21= new Item("The Erasers by Alain Robbe-Grillet, signed", 1, BigDecimal.valueOf(400.0), "old signed book", Categories.BOOKS);
        i21.setOwner(sofia);
        i21.addImage(new URL("https://pictures.abebooks.com/MHORNBURG/md/md9872514197.jpg"));
        itemRepository.saveAndFlush(i21);
        i21.setCreationDate(Date.from(LocalDate.of(2020, 4, 25).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i22= new Item("Warriors of the Rainbow by William Willoya and Vinson Brown", 1, BigDecimal.valueOf(35.0), "book is signed", Categories.BOOKS);
        i22.setOwner(ana);
        i22.addImage(new URL("https://pictures.abebooks.com/K86938/md/md15007802923.jpg"));
        itemRepository.saveAndFlush(i22);
        i22.setCreationDate(Date.from(LocalDate.of(2020, 4, 23).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        Item i23= new Item("The Man Who Lost His Wife, SYMONS, Julian", 1, BigDecimal.valueOf(64.0), "signed old book", Categories.BOOKS);
        i23.setOwner(miguel);
        i23.addImage(new URL("https://pictures.abebooks.com/HEARTS1956HEARTS/md/md30299836032.jpg"));
        itemRepository.saveAndFlush(i23);
        i23.setCreationDate(Date.from(LocalDate.of(2020, 4, 29).atStartOfDay(ZoneId.of(DBTIMEZONE)).toInstant()));

        logger.debug("Loaded "+itemService.getAll().size()+" items.");

        //loading comments

        commentService.deleteAll();

        Comment c1=new Comment("Em bom estado!", carlos, i6);
        commentRepository.saveAndFlush(c1);
        
        Comment c2=new Comment("5 estrelas", pedro, i3);
        commentRepository.saveAndFlush(c2);
        
        Comment c3=new Comment("Tinha uma página rasgada...", pedro, i2);
        commentRepository.saveAndFlush(c3);

        Comment c4=new Comment("Otimo", sofia, i15);
        commentRepository.saveAndFlush(c4);

        Comment c5=new Comment("Vendedor não responde...", miguel, i14);
        commentRepository.saveAndFlush(c5);

        Comment c6=new Comment("Wow", carlos, i13);
        commentRepository.saveAndFlush(c6);

        Comment c7=new Comment("Em otimas condições", ana, i12);
        commentRepository.saveAndFlush(c7);

        Comment c8=new Comment("Se calhar devia baixar o preço", pedro, i11);
        commentRepository.saveAndFlush(c8);

        Comment c9=new Comment("Confio no vendedor", carlos, i10);
        commentRepository.saveAndFlush(c9);

        Comment c10=new Comment("Funciona", sofia, i9);
        commentRepository.saveAndFlush(c10);

        Comment c11=new Comment("Incontactavel", miguel, i8);
        commentRepository.saveAndFlush(c11);

        Comment c12=new Comment("Scam", miguel, i7);
        commentRepository.saveAndFlush(c12);

        Comment c13=new Comment("Muito raro", ana, i4);
        commentRepository.saveAndFlush(c13);

        Comment c14=new Comment("Esse preço é muito baixo, acredite", miguel, i5);
        commentRepository.saveAndFlush(c14);

        Comment c15=new Comment("A transação correu muito bem", ana, i1);
        commentRepository.saveAndFlush(c15);

        Comment c16=new Comment("Bom livro", miguel, i16);
        commentRepository.saveAndFlush(c16);

        Comment c17=new Comment("Grande livro", miguel, i17);
        commentRepository.saveAndFlush(c17);

        Comment c18=new Comment("Enviei pm", ana, i18);
        commentRepository.saveAndFlush(c18);

        Comment c19=new Comment("Mandei msg!", sofia, i19);
        commentRepository.saveAndFlush(c19);

        Comment c20=new Comment("Quero!!", pedro, i20);
        commentRepository.saveAndFlush(c20);

        Comment c21=new Comment("Wow que raro", pedro, i21);
        commentRepository.saveAndFlush(c21);

        Comment c22=new Comment("Muito dinheiro", carlos, i22);
        commentRepository.saveAndFlush(c22);

        Comment c23=new Comment("Como arranjou?", ana, i23);
        commentRepository.saveAndFlush(c23);

        Comment c24=new Comment("Ando há anos à procura", carlos, i22);
        commentRepository.saveAndFlush(c24);

        Comment c25=new Comment("Responda por favor", pedro, i23);
        commentRepository.saveAndFlush(c25);

        logger.debug("Loaded "+commentService.getAll().size()+" comments.");

    }
}