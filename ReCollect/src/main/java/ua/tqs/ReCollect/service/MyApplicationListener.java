// package ua.tqs.ReCollect.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.ApplicationListener;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
// import ua.tqs.ReCollect.entity.Category;
// import ua.tqs.ReCollect.entity.Item;
// import ua.tqs.ReCollect.entity.Localizacao;
// import ua.tqs.ReCollect.entity.User;
// import ua.tqs.ReCollect.repository.CategoryRepository;
// import ua.tqs.ReCollect.repository.ItemRepository;
// import ua.tqs.ReCollect.repository.UserRepository;
// import ua.tqs.ReCollect.utils.Image;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.logging.Logger;

// @Component
// @Order(0)
// public class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

//     private static final String placeHolderIMGurl_COIN = "https://sammydvintage.com/wp-content/uploads/2019/02/pexels-photo-325154.jpeg";
//     private static final String placeHolderIMGurl_BD = "https://cdn.catawiki.net/assets/marketing/stories-images/4757-7326c52efe4952575f17182fdd3944dcb079c2ba-og_image.jpg";
//     private static final String placeHolderIMGurl_GTABundle = "https://www.justpushstart.com/wp-content/uploads/2013/05/Grand-Theft-Auto-V-CE.jpg";

//     @Autowired
//     private CategoryRepository categoryRepository;

//     @Autowired
//     private ItemService itemService;

//     @Autowired
//     private UserService userService;

//     private static final Logger logger = Logger.getLogger("ApplicationListener#onApplicationEvent()");

//     @Override
//     public void onApplicationEvent(ApplicationReadyEvent event) {
//         logger.info("ApplicationListener#onApplicationEvent()");
//         List<Category> categoriesList = initSomeCategories();
//         System.err.println("categorias: " + categoriesList.toString());
//         //logger.info("categorias: " + categoriesList.toString());

//         //initSomeItems();

//         List<User> userList = initUsersAndItems();
//        // System.err.println("users: " + userList.toString());
//     }

//     public List<User> initUsersAndItems(){
//         //List<Item> allItems = new ArrayList<>();

//         // setup some images
//         List<String> imagens1 = new ArrayList<>();
//         imagens1.add(placeHolderIMGurl_COIN);
//         imagens1.add(placeHolderIMGurl_COIN);
//         imagens1.add(placeHolderIMGurl_COIN);
//         imagens1.add(placeHolderIMGurl_COIN);
//         imagens1.add(placeHolderIMGurl_COIN);

//         List<String> imagens2 = new ArrayList<>();
//         imagens2.add(placeHolderIMGurl_BD);
//         imagens2.add(placeHolderIMGurl_BD);
//         imagens2.add(placeHolderIMGurl_BD);

//         List<String> imagens3 = new ArrayList<>();
//         imagens3.add(placeHolderIMGurl_GTABundle);


//         Item i1 = new Item("Escudo de 1987", 9.99, 11, "Escudo de prata antes de Portugal entrar na UE", "Miscellaneous");
//         i1.setImagens(imagens1);
//         Item i2 = new Item("Banda Desenhada Marvel", 19.99, 1, "Edição comemorativa 2000", "Books");
//         i2.setImagens(imagens2);
//         Item i3 = new Item("GTA V - Edição de colecionador", 65, 1, "Bundle com cópia do jogo, poster do mapa de Los Santos, boné, steelbook e bolsa. Lançado em 2013", "Games");
//         i3.setImagens(imagens3);

//         Localizacao ilhavo = new Localizacao("Aveiro", "Ílhavo");
//         Localizacao ansiao = new Localizacao("Leiria", "Ansiao");
//         Localizacao sabugal = new Localizacao("Guarda", "Sabugal");

//         //(String name, String password, String email, Localizacao localizacao)
//         User andre = new User("André", "pass", "andre@email.pt", ilhavo);
//         User alex = new User("Alex", "pass", "alex@email.pt", ansiao);
//         User laura = new User("Laura", "pass", "laura@email.pt", sabugal);


//         //andre.addItem(i2);
//         //alex.addItem(i3);
//         //laura.addItem(i1);

//         userService.save(andre);
//         userService.save(alex);
//         userService.save(laura);

//         itemService.save(i1);
//         itemService.save(i2);
//         itemService.save(i3);


//         return userService.getAllUsers();
//     }

//     /*private void initSomeItems(){
//         itemRepository.saveAll(generateItems());
//     }*/

//     private List<Item> generateItems(){
//         // Item(String nome, double preco, int quantidade, String descricao, String categoria)

//         List<Item> allItems = new ArrayList<>();

//         // setup some images
//         List<String> imagens1 = new ArrayList<>();
//         imagens1.add(placeHolderIMGurl_COIN);
//         imagens1.add(placeHolderIMGurl_COIN);
//         imagens1.add(placeHolderIMGurl_COIN);
//         imagens1.add(placeHolderIMGurl_COIN);
//         imagens1.add(placeHolderIMGurl_COIN);

//         List<String> imagens2 = new ArrayList<>();
//         imagens2.add(placeHolderIMGurl_BD);
//         imagens2.add(placeHolderIMGurl_BD);
//         imagens2.add(placeHolderIMGurl_BD);

//         List<String> imagens3 = new ArrayList<>();
//         imagens3.add(placeHolderIMGurl_GTABundle);


//         Item i1 = new Item("Escudo de 1987", 9.99, 11, "Escudo de prata antes de Portugal entrar na UE", "Miscellaneous");
//         i1.setImagens(imagens1);
//         Item i2 = new Item("Banda Desenhada Marvel", 19.99, 1, "Edição comemorativa 2000", "Books");
//         i2.setImagens(imagens2);
//         Item i3 = new Item("GTA V - Edição de colecionador", 65, 1, "Bundle com cópia do jogo, poster do mapa de Los Santos, boné, steelbook e bolsa. Lançado em 2013", "Games");
//         i3.setImagens(imagens3);

//         allItems.add(i1);
//         allItems.add(i2);
//         allItems.add(i3);

//         return allItems;
//     }


//     private List<Category> initSomeCategories(){
//         Category books = new Category("Books", "fas fa-book-open icon-bg-1");
//         Category games = new Category("Games", "fas fa-dice-d6  icon-bg-2");
//         Category toys = new Category("Toys", "fas fa-robot icon-bg-3");
//         Category tech = new Category("Technology", "fa fa-camera-retro icon-bg-4");
//         Category music = new Category("Music", "fas fa-guitar icon-bg-5");
//         Category art = new Category("Art", "fas fa-image icon-bg-6");
//         Category misc = new Category("Miscellaneous", "fas fa-box-open icon-bg-7");

//         categoryRepository.save(books);
//         categoryRepository.save(games);
//         categoryRepository.save(toys);
//         categoryRepository.save(tech);
//         categoryRepository.save(music);
//         categoryRepository.save(art);
//         categoryRepository.save(misc);

//         return categoryRepository.findAll();

//     }
// }
