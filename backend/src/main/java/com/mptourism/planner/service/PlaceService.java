package com.mptourism.planner.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceService {

    public static class PhotoSpot {
        public String id;
        public String title;
        public String imageUrl;
        public String story;
        public String timings;
        public String entryFee;

        public PhotoSpot(String id, String title, String imageUrl, String story, String timings, String entryFee) {
            this.id = id;
            this.title = title;
            this.imageUrl = imageUrl;
            this.story = story;
            this.timings = timings;
            this.entryFee = entryFee;
        }
    }

    public static class NearbyAttraction {
        public String id;
        public String name;
        public double distanceKm;
        public double estimatedCostPerPerson;
        public String description;
        public String imageUrl;
        public String category;

        public NearbyAttraction(String id, String name, double distanceKm, double estimatedCostPerPerson, String description, String imageUrl, String category) {
            this.id = id;
            this.name = name;
            this.distanceKm = distanceKm;
            this.estimatedCostPerPerson = estimatedCostPerPerson;
            this.description = description;
            this.imageUrl = imageUrl;
            this.category = category;
        }
    }

    public static class HotelOption {
        public String id;
        public String name;
        public double rating;
        public double pricePerNight;
        public String imageUrl;
        public List<String> amenities;

        public HotelOption(String id, String name, double rating, double pricePerNight, String imageUrl, List<String> amenities) {
            this.id = id;
            this.name = name;
            this.rating = rating;
            this.pricePerNight = pricePerNight;
            this.imageUrl = imageUrl;
            this.amenities = amenities;
        }
    }

    public static class Destination {
        public String id;
        public String name;
        public String tagline;
        public String category;
        public double mapX; // percentage on map SVG
        public double mapY;
        public double rating;
        public String heroImage;
        public String description;
        public String bestTime;
        public List<PhotoSpot> gallery;
        public List<NearbyAttraction> nearby;
        public List<HotelOption> hotels;

        public Destination(String id, String name, String tagline, String category, double mapX, double mapY, double rating, String heroImage, String description, String bestTime, List<PhotoSpot> gallery, List<NearbyAttraction> nearby, List<HotelOption> hotels) {
            this.id = id;
            this.name = name;
            this.tagline = tagline;
            this.category = category;
            this.mapX = mapX;
            this.mapY = mapY;
            this.rating = rating;
            this.heroImage = heroImage;
            this.description = description;
            this.bestTime = bestTime;
            this.gallery = gallery;
            this.nearby = nearby;
            this.hotels = hotels;
        }
    }

    private final Map<String, Destination> destinationsMap = new LinkedHashMap<>();

    public PlaceService() {
        initDestinations();
    }

    private void initDestinations() {
        // 1. Ujjain
        destinationsMap.put("ujjain", new Destination(
            "ujjain", "Ujjain", "The City of Mahakal & Sacred Shipra River", "Spiritual",
            34.0, 59.0, 4.9,
            "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=1200&q=80",
            "Ujjain is one of the seven sacred cities (Sapta Puri) of Hinduism, located on the eastern bank of the Shipra River. Known for the world-famous Mahakaleshwar Jyotirlinga and the grand Simhastha Kumbh Mela.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("u1", "Mahakaleshwar Temple", "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80", "One of the 12 sacred Jyotirlingas in India. The shrine of Mahakaleshwar faces south, known as Dakshinamurti, which is unique among all Jyotirlingas. Famous for the early morning Bhasma Aarti.", "4:00 AM - 11:00 PM", "Free / VIP ₹250"),
                new PhotoSpot("u2", "Harsiddhi Mata Mandir", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=800&q=80", "One of the 51 Shakti Peethas where Goddess Parvati's elbow fell. Features two towering 50-foot lamp columns (Deepastambhas) illuminated during evening Sandhya Aarti.", "5:00 AM - 10:00 PM", "Free"),
                new PhotoSpot("u3", "Ram Ghat Aarti", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=800&q=80", "The oldest bathing ghat on Shipra River. Site of the holy Kumbh dip. Every evening, thousands gather for the mesmerising Shipra Aarti with brass lamps and devotional hymns.", "6:00 PM - 7:30 PM", "Free"),
                new PhotoSpot("u4", "Kal Bhairav Temple", "https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80", "Unique ancient temple dedicated to Kal Bhairav, the guardian deity of Ujjain, where liquor is offered as prasad to the deity. Mentioned in Avanti Khanda of Skanda Purana.", "6:00 AM - 10:00 PM", "Free")
            ),
            Arrays.asList(
                new NearbyAttraction("n1", "Mangalnath Mandir", 5.5, 150, "The birthplace of planet Mars (Mangal) as per Vedic astrology.", "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=600&q=80", "Spiritual"),
                new NearbyAttraction("n2", "ISKCON Ujjain", 3.2, 100, "Stunning white marble temple complex with serene vegetarian prasad dining.", "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=600&q=80", "Culture"),
                new NearbyAttraction("n3", "Kaliadeh Palace", 8.0, 200, "Historic water palace built on an island in Shipra River by Sultan of Mandu in 1458 AD.", "https://images.unsplash.com/photo-1513694203232-719a280e022f?auto=format&fit=crop&w=600&q=80", "Heritage"),
                new NearbyAttraction("n4", "Vedh Shala (Observatory)", 2.8, 80, "Astronomical observatory built by Maharaja Sawai Jai Singh II in 1725 AD.", "https://images.unsplash.com/photo-1507676184212-d03ab07a01bf?auto=format&fit=crop&w=600&q=80", "Heritage")
            ),
            Arrays.asList(
                new HotelOption("h1", "Hotel Avantika Grand", 4.7, 3500, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Free WiFi", "Breakfast Included", "Near Mahakal Temple", "AC Rooms")),
                new HotelOption("h2", "MP Tourism Resort Shipra", 4.5, 2800, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("River View", "Restaurant", "Garden", "Parking")),
                new HotelOption("h3", "Mahakal Heritage Stay", 4.2, 1800, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", Arrays.asList("Budget Friendly", "AC Rooms", "24h Room Service"))
            )
        ));

        // 2. Gwalior
        destinationsMap.put("gwalior", new Destination(
            "gwalior", "Gwalior", "City of Forts, Royalty & Classical Music", "Heritage",
            37.0, 15.0, 4.8,
            "https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=1200&q=80",
            "Dominating the skyline with its massive 8th-century hill fortress, Gwalior is celebrated for its royal Palaces, Scindia legacy, and rich musical heritage of Tansen.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("g1", "Gwalior Fort", "https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=800&q=80", "Described by Emperor Babur as 'the pearl among fortresses of Hind'. Houses Man Singh Palace with vibrant blue tilework and centuries of royal sagas.", "6:00 AM - 6:00 PM", "₹75 / Foreigner ₹300"),
                new PhotoSpot("g2", "Jai Vilas Palace", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80", "Grand 19th-century palace blending Tuscan and Corinthian architecture. Features the famous 3.5-tonne chandeliers and the silver model train serving after-dinner cigars.", "10:00 AM - 5:00 PM (Closed Mon)", "₹300"),
                new PhotoSpot("g3", "Saas Bahu Temple", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=800&q=80", "11th-century twin stone temples built by King Mahipala with intricate carvings dedicated to Lord Vishnu.", "8:00 AM - 6:00 PM", "₹25"),
                new PhotoSpot("g4", "Tomb of Tansen", "https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80", "Memorial of music legend Tansen, one of the Navratnas of Emperor Akbar. Host venue for the annual Tansen Music Festival.", "8:00 AM - 6:00 PM", "Free")
            ),
            Arrays.asList(
                new NearbyAttraction("ng1", "Tighra Dam", 23.0, 300, "Picturesque freshwater dam featuring speed boating, water sports, and scenic hill views.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature"),
                new NearbyAttraction("ng2", "Bateshwar Temples", 35.0, 450, "Group of nearly 200 sandstone Hindu temples restored by ASI deep inside Morena forest.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage"),
                new NearbyAttraction("ng3", "Mitawali Padavali Temple", 40.0, 400, "Circular 64 Yogini temple that inspired the architectural design of India's Parliament House.", "https://images.unsplash.com/photo-1564507592333-c60657eea523?auto=format&fit=crop&w=600&q=80", "Heritage")
            ),
            Arrays.asList(
                new HotelOption("hg1", "Taj Usha Kiran Palace", 4.9, 9500, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("5-Star Luxury", "Heritage Property", "Swimming Pool", "Spa")),
                new HotelOption("hg2", "MP Tourism Tansen Residency", 4.4, 3200, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Central Location", "Restaurant", "WiFi", "Bar"))
            )
        ));

        // 3. Khajuraho
        destinationsMap.put("khajuraho", new Destination(
            "khajuraho", "Khajuraho", "UNESCO World Heritage Temple Architecture", "Heritage",
            69.0, 30.0, 4.9,
            "https://images.unsplash.com/photo-1626082896492-766af4eb6501?auto=format&fit=crop&w=1200&q=80",
            "World famous UNESCO World Heritage site renowned for stunning 10th-century Chandela temples celebrating life, spirituality, love, and erotic art in sandstone perfection.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("k1", "Kandariya Mahadeva Temple", "https://images.unsplash.com/photo-1626082896492-766af4eb6501?auto=format&fit=crop&w=800&q=80", "The largest and most ornate Hindu temple in Khajuraho complex with 870 detailed statues representing cosmic balance.", "6:00 AM - 6:00 PM", "₹40 / Foreigner ₹600"),
                new PhotoSpot("k2", "Lakshmana Temple", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80", "Exquisite 10th-century Vaikuntha Vishnu temple preserved intact with elaborate friezes depicting warriors, elephants, and courtly life.", "6:00 AM - 6:00 PM", "Included in Complex Ticket"),
                new PhotoSpot("k3", "Raneh Falls & Canyon", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80", "Dramatic 30-meter deep gorge of crystalline granite in shades of pink, green, and grey on the Ken River.", "8:00 AM - 5:00 PM", "₹50"),
                new PhotoSpot("k4", "Khajuraho Light & Sound Show", "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=800&q=80", "Immersive evening light show narrated by Amitabh Bachchan tracing Chandela dynasty history under open night sky.", "6:30 PM (English) / 7:30 PM (Hindi)", "₹300")
            ),
            Arrays.asList(
                new NearbyAttraction("nk1", "Panna National Park Safari", 25.0, 2500, "Tiger sanctuary along the Ken river with scenic gorges and vulture nesting cliffs.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                new NearbyAttraction("nk2", "Pandav Falls", 30.0, 150, "Natural waterfall creating a clear green lake, legendarily visited by Pandavas.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Nature")
            ),
            Arrays.asList(
                new HotelOption("hk1", "The Lalit Temple View Khajuraho", 4.8, 8500, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Temple View", "Pool", "Luxury Spa", "Buffet Breakfast")),
                new HotelOption("hk2", "Hotel Chandela Khajuraho", 4.4, 3800, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Lush Gardens", "Swimming Pool", "Restaurant"))
            )
        ));

        // 4. Sanchi
        destinationsMap.put("sanchi", new Destination(
            "sanchi", "Sanchi", "Cradle of Buddhist Art & Emperor Ashoka's Stupa", "Spiritual",
            48.0, 48.0, 4.8,
            "https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=1200&q=80",
            "UNESCO World Heritage Buddhist hub commissioned by Emperor Ashoka in the 3rd century BCE, boasting preserved stone Torana gateways detailing Buddha's life Jatakas.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("s1", "The Great Stupa 1", "https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=800&q=80", "The oldest stone structure in India built by Emperor Ashoka over Buddha relics, surrounded by four carved ceremonial toranas.", "6:30 AM - 6:30 PM", "₹40 / Foreigner ₹600"),
                new PhotoSpot("s2", "Ashoka Pillar Sanchi", "https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80", "Polished Mauryan sandstone pillar dating to 3rd century BCE featuring Ashoka's edicts.", "6:30 AM - 6:30 PM", "Included"),
                new PhotoSpot("s3", "Sanchi Archaeological Museum", "https://images.unsplash.com/photo-1566127444979-b3d2b654e3d7?auto=format&fit=crop&w=800&q=80", "Houses the famous 4-lion capital head of Ashoka, ancient Buddhist sculptures, and coins.", "10:00 AM - 5:00 PM", "₹10")
            ),
            Arrays.asList(
                new NearbyAttraction("ns1", "Udayagiri Caves", 12.0, 120, "20 rock-cut caves from Gupta period (4th century) featuring iconic Varaha (boar avatar) panel.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage"),
                new NearbyAttraction("ns2", "Heliodorus Pillar (Khamba Baba)", 18.0, 50, "Monolithic stone pillar erected around 110 BCE in Vidisha by Greek envoy Heliodorus.", "https://images.unsplash.com/photo-1564507592333-c60657eea523?auto=format&fit=crop&w=600&q=80", "Heritage")
            ),
            Arrays.asList(
                new HotelOption("hs1", "MP Tourism Gateway Retreat Sanchi", 4.5, 3000, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Hill View", "Restaurant", "AC Rooms", "Serene Ambience"))
            )
        ));

        // 5. Bhopal
        destinationsMap.put("bhopal", new Destination(
            "bhopal", "Bhopal", "City of Lakes & Regal Begum Heritage", "Culture",
            44.0, 53.0, 4.7,
            "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=1200&q=80",
            "The capital city of Madhya Pradesh seamlessly blends 11th-century lakes created by Raja Bhoj with grand mosques built by the ruling Begums and lush national parks.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("b1", "Upper Lake (Bhojtal)", "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=800&q=80", "Oldest man-made lake in India created in 11th century by Raja Bhoj. Popular for sunset boating, kayaking, and cruise trips.", "6:00 AM - 9:00 PM", "Free / Boat ₹150"),
                new PhotoSpot("b2", "Taj-ul-Masajid", "https://images.unsplash.com/photo-1564507592333-c60657eea523?auto=format&fit=crop&w=800&q=80", "One of the largest mosques in Asia with pink facade and 18-storey octagonal minarets.", "6:00 AM - 8:00 PM", "Free"),
                new PhotoSpot("b3", "Madhya Pradesh Tribal Museum", "https://images.unsplash.com/photo-1566127444979-b3d2b654e3d7?auto=format&fit=crop&w=800&q=80", "Vibrant, world-class museum celebrating indigenous tribal culture, myths, and art of Gond and Bhil tribes.", "12:00 PM - 8:00 PM (Closed Mon)", "₹20"),
                new PhotoSpot("b4", "Van Vihar National Park", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80", "Open safari park situated alongside Upper Lake, home to Tigers, Panthers, Cheetal, and migratory birds.", "6:30 AM - 6:30 PM", "₹30")
            ),
            Arrays.asList(
                new NearbyAttraction("nb1", "Bhimbetka Rock Shelters", 45.0, 200, "UNESCO World Heritage Paleolithic rock paintings dating back over 30,000 years.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage"),
                new NearbyAttraction("nb2", "Bhojpur Shiv Mandir", 28.0, 150, "Home to the massive monolithic 18-foot Shiva Lingam housed in an unfinished 11th-century temple.", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=600&q=80", "Spiritual"),
                new NearbyAttraction("nb3", "Kerwa Dam", 15.0, 250, "Eco-tourism spot with longest zip-line, lush green forest trails, and waterside dining.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Adventure")
            ),
            Arrays.asList(
                new HotelOption("hb1", "Jehan Numa Palace Hotel", 4.9, 8900, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("19th Century Palace", "Pool", "Multiple Fine Dining", "Horse Riding")),
                new HotelOption("hb2", "MP Tourism Palash Residency", 4.3, 2600, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("City Center", "WiFi", "Restaurant", "Clean Rooms"))
            )
        ));

        // 6. Orchha
        destinationsMap.put("orchha", new Destination(
            "orchha", "Orchha", "The Hidden Gem on Betwa River", "Heritage",
            54.0, 28.0, 4.8,
            "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=1200&q=80",
            "Founded in the 16th century by Bundela chief Rudra Pratap, Orchha freezes time with imposing fort palaces, river chhatris, and the unique Ram Raja Temple where Lord Ram is worshipped as King.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("o1", "Jahangir Mahal", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80", "Multi-storeyed Mughal-Bundela palace built by Bir Singh Deo to welcome Emperor Jahangir with balconies, stone chhatris, and courtyards.", "9:00 AM - 5:00 PM", "₹10"),
                new PhotoSpot("o2", "Ram Raja Temple", "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80", "The only temple in India where Lord Ram is venerated as King with a daily gun salute by police guards.", "8:00 AM - 10:00 PM", "Free"),
                new PhotoSpot("o3", "Chhatris on Betwa River", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=800&q=80", "14 magnificent royal cenotaphs lining the peaceful banks of Betwa river, glowing golden at sunset.", "7:00 AM - 6:30 PM", "₹10")
            ),
            Arrays.asList(
                new NearbyAttraction("no1", "Betwa River Rafting", 1.5, 800, "River rafting thrill along the Bundelkhand rocky river rapids.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Adventure"),
                new NearbyAttraction("no2", "Orchha Wildlife Sanctuary", 3.0, 150, "Nature reserve with spotted deer, jackals, blue bulls, and river birds.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife")
            ),
            Arrays.asList(
                new HotelOption("ho1", "MP Tourism Betwa Retreat", 4.6, 3400, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("River View", "Heritage Cottages", "Pool", "Restaurant")),
                new HotelOption("ho2", "Amar Mahal Orchha", 4.7, 5800, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Royal Ambience", "Swimming Pool", "Ayurveda Spa"))
            )
        ));

        // 7. Jabalpur
        destinationsMap.put("jabalpur", new Destination(
            "jabalpur", "Jabalpur", "City of Marble Rocks & Roaring Waterfalls", "Nature",
            67.0, 58.0, 4.8,
            "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=1200&q=80",
            "Flanked by the Narmada River, Jabalpur is famous for the towering white marble gorges of Bhedaghat, the cascading Dhuandhar waterfall, and ancient Gond forts.",
            "October to April",
            Arrays.asList(
                new PhotoSpot("j1", "Dhuandhar Falls", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80", "The Narmada plunges 30 meters creating a mist so thick it looks like smoke (Dhuan). Cable car ride provides aerial views.", "6:00 AM - 8:00 PM", "Free / Cable Car ₹100"),
                new PhotoSpot("j2", "Marble Rocks Boating", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80", "Rowing between 100-foot gleaming magnesium limestone rocks that change colors under moonlight.", "6:00 AM - 7:00 PM", "₹120 per head"),
                new PhotoSpot("j3", "Chausath Yogini Temple", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=800&q=80", "10th-century circular hilltop temple housing 64 yogini statues overlooking the Narmada gorge.", "7:00 AM - 6:00 PM", "Free"),
                new PhotoSpot("j4", "Balancing Rock & Madan Mahal", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=800&q=80", "Natural geological wonder of a massive rock balancing on a tiny point, survived the 1997 earthquake!", "8:00 AM - 6:00 PM", "Free")
            ),
            Arrays.asList(
                new NearbyAttraction("nj1", "Bargi Dam Cruise", 40.0, 350, "Huge reservoir offering motor boat cruises, speed boats, and eco-resort dining.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature"),
                new NearbyAttraction("nj2", "Dumna Nature Reserve", 10.0, 100, "Eco-tourism park with deer, wild boar, canopy walk, and kayaking.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Nature")
            ),
            Arrays.asList(
                new HotelOption("hj1", "MP Tourism Marble Rocks Bhedaghat", 4.5, 3100, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Overlooking Gorge", "Restaurant", "Balcony Views")),
                new HotelOption("hj2", "Hotel Kalchuri Grand", 4.3, 2700, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Near Station", "WiFi", "Conference Rooms"))
            )
        ));

        // 8. Indore
        destinationsMap.put("indore", new Destination(
            "indore", "Indore", "Commercial Capital & Street Food Paradise", "Culture",
            30.0, 62.0, 4.9,
            "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=1200&q=80",
            "Consistently voted India's cleanest city, Indore combines Holkar royal elegance with mouth-watering food paradises like Sarafa Night Market and Chappan Dukan.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("i1", "Rajwada Palace", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80", "7-storey royal palace of the Holkar dynasty combining Maratha, Mughal, and French architectural styles.", "10:00 AM - 5:00 PM", "₹20"),
                new PhotoSpot("i2", "Sarafa Bazaar Night Food Market", "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=800&q=80", "Jewelry street by day that turns into a bustling midnight street food hub serving Bhutte Ka Kees, Garadu, and Jaleba.", "8:00 PM - 2:00 AM", "Free entry"),
                new PhotoSpot("i3", "Lal Bagh Palace", "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80", "Grandest Holkar palace with gates modelled after Buckingham Palace, Italian marble floors, and Versailles chandeliers.", "10:00 AM - 5:00 PM (Closed Mon)", "₹20"),
                new PhotoSpot("i4", "Chappan Dukan", "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=800&q=80", "Street lined with 56 legendary food stalls serving Poha Jalebi, Chaat, Hot Dog, and Shrikhand.", "6:00 AM - 11:00 PM", "Free entry")
            ),
            Arrays.asList(
                new NearbyAttraction("ni1", "Ralamandal Wildlife Sanctuary", 12.0, 100, "Oldest wildlife sanctuary in MP with trekking paths and Holkar hunting lodge.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Nature"),
                new NearbyAttraction("ni2", "Tincha Falls", 25.0, 150, "Breathtaking 300-foot monsoon waterfall nestled in greenery.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Waterfall")
            ),
            Arrays.asList(
                new HotelOption("hi1", "Sayaji Hotel Indore", 4.9, 6500, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("5-Star", "Multiple Restaurants", "Pool", "Bowling Alley")),
                new HotelOption("hi2", "Lemon Tree Hotel Indore", 4.4, 3200, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Centrally Located", "Fitness Center", "WiFi"))
            )
        ));

        // 9. Pachmarhi
        destinationsMap.put("pachmarhi", new Destination(
            "pachmarhi", "Pachmarhi", "Queen of Satpura Hill Station", "Nature",
            52.0, 71.0, 4.9,
            "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1200&q=80",
            "Madhya Pradesh's only hill station, nestled at 1,067 meters in the Satpura range with lush pine forests, waterfalls, ancient caves, and Dhoopgarh peak.",
            "October to June",
            Arrays.asList(
                new PhotoSpot("p1", "Bee Falls (Jamuna Prapat)", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80", "Spectacular 35-meter waterfall providing fresh hill water supply to Pachmarhi town.", "9:00 AM - 5:00 PM", "₹15"),
                new PhotoSpot("p2", "Dhoopgarh Sunset Point", "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80", "Highest peak in Madhya Pradesh (1,352m) offering mesmerizing 360-degree sunset views over Satpura ranges.", "6:00 AM - 6:30 PM", "Forest Permit Required"),
                new PhotoSpot("p3", "Pandav Caves", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=800&q=80", "Five 9th-century rock-cut Buddhist caves where the Pandava brothers spent exile as per legend.", "8:00 AM - 6:00 PM", "Free"),
                new PhotoSpot("p4", "Jata Shankar Cave Temple", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=800&q=80", "Natural cave with stalactites resembling Shiva's matted hair, housing natural rock lingams.", "7:00 AM - 6:00 PM", "Free")
            ),
            Arrays.asList(
                new NearbyAttraction("np1", "Satpura Tiger Reserve Safari", 15.0, 3000, "Jeep safari, canoe safari, and walking safari deep in tiger country.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                new NearbyAttraction("np2", "Apsara Vihar (Fairy Pool)", 3.0, 50, "Gentle waterfall forming a clean natural swimming pool, ideal for families.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Nature")
            ),
            Arrays.asList(
                new HotelOption("hp1", "MP Tourism Rock End Manor", 4.8, 4800, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Heritage Bungalow", "Colonial Lawn", "Fireplace", "Hill View")),
                new HotelOption("hp2", "MP Tourism Champak Bungalow", 4.4, 2900, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Lush Garden", "Family Rooms", "Restaurant"))
            )
        ));

        // 10. Kanha National Park
        destinationsMap.put("kanha", new Destination(
            "kanha", "Kanha National Park", "The Original Jungle Book Sanctuary", "Wildlife",
            78.0, 68.0, 4.9,
            "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=1200&q=80",
            "Inspiration for Rudyard Kipling's 'The Jungle Book', Kanha is famous for Royal Bengal Tigers, the rare Barasingha (swamp deer), and dense Sal and Bamboo forests.",
            "October to June",
            Arrays.asList(
                new PhotoSpot("kn1", "Kanha Tiger Safari (Khatia Gate)", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80", "Early morning 4x4 open Gypsy safari offering thrilling encounters with Bengal Tigers, Leopards, and Sloth Bears.", "6:00 AM - 11:00 AM / 3:00 PM - 6:00 PM", "₹3,500 per Jeep"),
                new PhotoSpot("kn2", "Bamni Dadar Sunset Point", "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80", "Highest plateau in Kanha known as Sunset Point, where herds of Sambhar and Gaur graze against dramatic orange skies.", "4:30 PM - 6:30 PM", "Included in Safari"),
                new PhotoSpot("kn3", "Kanha Museum", "https://images.unsplash.com/photo-1566127444979-b3d2b654e3d7?auto=format&fit=crop&w=800&q=80", "Interprets park ecology, tiger conservation history, skeleton displays, and tribal culture.", "8:00 AM - 5:00 PM", "₹20")
            ),
            Arrays.asList(
                new NearbyAttraction("nkn1", "Mukki Gate Zone Safari", 20.0, 3500, "Renowned for high tiger sighting frequency and open grasslands.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                new NearbyAttraction("nkn2", "Kawardha Palace Visit", 75.0, 500, "Heritage palace built by Maharaja Dharamraj Singh in Italian marble.", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=600&q=80", "Heritage")
            ),
            Arrays.asList(
                new HotelOption("hkn1", "Baghira Jungle Resort Kanha", 4.7, 4500, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Inside Forest", "Swimming Pool", "Safari Booking Help", "Buffet Meals")),
                new HotelOption("hkn2", "Kanha Jungle Lodge", 4.8, 7200, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Luxury Eco Resort", "Naturalist Guides", "Organic Dining"))
            )
        ));

        // 11. Omkareshwar
        destinationsMap.put("omkareshwar", new Destination(
            "omkareshwar", "Omkareshwar", "Island Jyotirlinga Shaped Like 'OM'", "Spiritual",
            31.0, 68.0, 4.8,
            "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=1200&q=80",
            "Sacred island shaped like the holy chant 'OM' in the Narmada River, housing one of the 12 revered Shiva Jyotirlingas along with the massive 108ft Statue of Oneness (Adi Shankaracharya).",
            "October to March",
            Arrays.asList(
                new PhotoSpot("om1", "Omkareshwar Jyotirlinga Temple", "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80", "5-storey multi-pillared temple on Mandhata island dedicated to Lord Shiva.", "5:00 AM - 9:30 PM", "Free"),
                new PhotoSpot("om2", "Statue of Oneness (Adi Shankaracharya)", "https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80", "Newly inaugurated 108-foot colossal bronze statue of young Adi Shankaracharya atop Mandhata hill.", "9:00 AM - 7:00 PM", "Free"),
                new PhotoSpot("om3", "Mamleshwar Temple", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=800&q=80", "Located on the south bank of Narmada, considered half of the main Omkareshwar Jyotirlinga worship.", "6:00 AM - 9:00 PM", "Free")
            ),
            Arrays.asList(
                new NearbyAttraction("nom1", "Narmada Parikrama Bridge", 1.0, 50, "Suspension bridge offering panoramic river views.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Spiritual"),
                new NearbyAttraction("nom2", "Kaveri Sangam Ghat", 3.0, 100, "Confluence of Kaveri and Narmada rivers, holy spot for ritual dips.", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=600&q=80", "Spiritual")
            ),
            Arrays.asList(
                new HotelOption("hom1", "MP Tourism Narmada Resort Omkareshwar", 4.4, 2800, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("River Facing", "Restaurant", "Clean AC Rooms"))
            )
        ));

        // 12. Maheshwar
        destinationsMap.put("maheshwar", new Destination(
            "maheshwar", "Maheshwar", "Ahilya Fort & Handloom Weaving Center", "Heritage",
            27.0, 69.0, 4.8,
            "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=1200&q=80",
            "The capital of the saintly Queen Ahilyabai Holkar on Narmada River, famous for majestic stone ghats, Ahilya Fort, and world-renowned Maheshwari silk sarees.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("mh1", "Ahilya Fort & Ghats", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=800&q=80", "High stone fort overlooking magnificent sweeping stairs leading into Narmada river, featured in movies like Padman and Manikarnika.", "7:00 AM - 7:00 PM", "Free"),
                new PhotoSpot("mh2", "Rehwa Society Handloom Center", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80", "Non-profit society established by the Royal family preserving traditional Maheshwari saree weaving.", "10:00 AM - 6:00 PM", "Free"),
                new PhotoSpot("mh3", "Baneshwar Temple", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=800&q=80", "Ancient temple built on an island right in the center of Narmada River.", "7:00 AM - 6:00 PM", "Boat trip ₹100")
            ),
            Arrays.asList(
                new NearbyAttraction("nmh1", "Narmada Sunset Boat Cruise", 0.5, 150, "Sunset wooden boat ride with lighted diyas floated on Narmada water.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature"),
                new NearbyAttraction("nmh2", "Sahastradhara Waterfall", 5.0, 200, "Where Narmada river breaks into a thousand streams over rock beds.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Waterfall")
            ),
            Arrays.asList(
                new HotelOption("hmh1", "Ahilya Fort Heritage Hotel", 4.9, 12000, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Royal Luxury", "Pool", "Organic Garden Dining")),
                new HotelOption("hmh2", "MP Tourism Narmada Retreat Maheshwar", 4.5, 3100, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("River Frontage", "Lawns", "Restaurant"))
            )
        ));

        // 13. Shivpuri
        destinationsMap.put("shivpuri", new Destination(
            "shivpuri", "Shivpuri", "Royal Summer Capital & Scindia Chhatris", "Heritage",
            39.0, 24.0, 4.6,
            "https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=1200&q=80",
            "Former summer capital of Scindia rulers of Gwalior, surrounded by dense Madhav National Park forests, marble cenotaphs, and tranquil lakes.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("sh1", "Madhav National Park", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80", "254 sq km reserve featuring George Castle at peak height, Sakhya Sagar lake, and leopard sightings.", "6:00 AM - 6:00 PM", "₹150"),
                new PhotoSpot("sh2", "Scindia Chhatris Shivpuri", "https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=800&q=80", "Exquisite marble cenotaphs of Madho Rao Scindia set in formal Mughal gardens with inlaid stone motifs.", "8:00 AM - 7:00 PM", "₹20"),
                new PhotoSpot("sh3", "Sakhya Sagar Boat Club", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80", "Lakeside boat club inside national park known for marsh crocodile sightings.", "9:00 AM - 5:00 PM", "₹100")
            ),
            Arrays.asList(
                new NearbyAttraction("nsh1", "Bhadaiya Kund", 4.0, 50, "Natural mineral spring believed to have medicinal skin curing properties.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Nature"),
                new NearbyAttraction("nsh2", "Narwar Fort", 42.0, 300, "Historic hilltop fort associated with Raja Nala and Damayanti romance.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage")
            ),
            Arrays.asList(
                new HotelOption("hsh1", "MP Tourism Tourist Village Shivpuri", 4.3, 2500, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Lakeside Cottages", "Restaurant", "Park Views"))
            )
        ));

        // 14. Bhedaghat
        destinationsMap.put("bhedaghat", new Destination(
            "bhedaghat", "Bhedaghat", "Magical Marble Canyons & Cable Car Ride", "Nature",
            66.0, 59.0, 4.9,
            "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1200&q=80",
            "Famous worldwide for white marble rock gorges towering 100 feet over the turquoise waters of Narmada River, located near Jabalpur.",
            "November to May",
            Arrays.asList(
                new PhotoSpot("bh1", "Panchvati Ghat Boating", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80", "Boatmen narrate humorous poetry as you sail through narrow marble walls that sparkle under sunlight.", "6:00 AM - 7:00 PM", "₹120"),
                new PhotoSpot("bh2", "Dhuandhar Viewpoint Cable Car", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80", "Cable car spanning across the roaring waterfall from east bank to west bank.", "9:00 AM - 6:00 PM", "₹100")
            ),
            Arrays.asList(
                new NearbyAttraction("nbh1", "Soapstone Handicrafts Market", 0.5, 100, "Local markets selling carved marble statues, keychains, and nameplates.", "https://images.unsplash.com/photo-1566127444979-b3d2b654e3d7?auto=format&fit=crop&w=600&q=80", "Shopping")
            ),
            Arrays.asList(
                new HotelOption("hbh1", "MP Tourism Motel Marble Rocks", 4.4, 2900, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Gorge View", "Restaurant", "Garden"))
            )
        ));

        // 15. Satna
        destinationsMap.put("satna", new Destination(
            "satna", "Satna", "Maihar Shardadevi & Ancient Bharhut Buddhist Relics", "Spiritual",
            72.0, 36.0, 4.6,
            "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=1200&q=80",
            "Gateway to the hilltop shrine of Maihar Sharda Devi and ancient Buddhist stupa sites of Bharhut.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("st1", "Maihar Devi Temple", "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80", "1063-step hilltop temple dedicated to Goddess Sharda, accessible by ropeway.", "5:00 AM - 9:00 PM", "Free / Ropeway ₹140"),
                new PhotoSpot("st2", "Bharhut Buddhist Stupa Site", "https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=800&q=80", "Historic 2nd century BCE Buddhist stupa site famed for carved stone railings.", "8:00 AM - 5:00 PM", "Free")
            ),
            Arrays.asList(
                new NearbyAttraction("nst1", "Chitrakoot Dham Tour", 75.0, 600, "Holy town where Lord Ram spent 11 years of exile on Mandakini river.", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=600&q=80", "Spiritual")
            ),
            Arrays.asList(
                new HotelOption("hst1", "Hotel Bharhut Satna (MP Tourism)", 4.2, 2200, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Near Station", "WiFi", "Restaurant"))
            )
        ));

        // 16. Bawangaja
        destinationsMap.put("bawangaja", new Destination(
            "bawangaja", "Bawangaja", "Colossal 84-Ft Monolithic Adinath Jain Statue", "Spiritual",
            21.0, 72.0, 4.7,
            "https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=1200&q=80",
            "Important Jain pilgrim site in Barwani district, home to a 54-foot (84 arm-length) monolithic statue of first Tirthankara Lord Adinath carved into mountain rock in the 12th century.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("bw1", "Lord Adinath Monolithic Statue", "https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80", "World's tallest monolithic statue of Lord Rishabhanatha carved out of brown rock in Satpura ranges.", "6:00 AM - 7:00 PM", "Free"),
                new PhotoSpot("bw2", "Chulgiri Hill Temples", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=800&q=80", "Complex of 11 hill temples located on the peak of Satpura range.", "6:00 AM - 6:00 PM", "Free")
            ),
            Arrays.asList(
                new NearbyAttraction("nbw1", "Narmada River View Barwani", 15.0, 100, "Serene river banks with scenic mountain backdrops.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature")
            ),
            Arrays.asList(
                new HotelOption("hbw1", "Bawangaja Jain Dharamshala & Guest House", 4.3, 1500, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", Arrays.asList("Pure Vegetarian", "Clean Rooms", "Pilgrim Parking"))
            )
        ));

        // 17. Mohadi Falls
        destinationsMap.put("mohadi_falls", new Destination(
            "mohadi_falls", "Mohadi Falls", "Unspoiled Gorge Waterfall & Nature Trek", "Waterfall",
            33.0, 64.0, 4.6,
            "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=1200&q=80",
            "Scenic waterfall located 30km from Indore, cascading down deep grassy valleys, popular for weekend treks and picnics.",
            "July to February",
            Arrays.asList(
                new PhotoSpot("mf1", "Mohadi Waterfall Valley View", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80", "Water plunging into a misty green gorge surrounded by Malwa plateau hills.", "6:00 AM - 6:00 PM", "Free")
            ),
            Arrays.asList(
                new NearbyAttraction("nmf1", "Choral Dam Eco Spot", 12.0, 150, "Quiet dam reservoir with speed boat rides and garden.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature")
            ),
            Arrays.asList(
                new HotelOption("hmf1", "Choral Resort MP Tourism", 4.3, 2400, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Lakeside", "Restaurant", "AC Rooms"))
            )
        ));

        // 18. Patalpani Waterfall
        destinationsMap.put("patalpani", new Destination(
            "patalpani", "Patalpani Waterfall", "300-Ft Waterfall & Heritage Railway Line", "Waterfall",
            32.0, 66.0, 4.7,
            "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=1200&q=80",
            "Legendary 300-foot waterfall near Mhow, named Patalpani because its pit is believed to reach the underworld (Patal). Crossed by a heritage railway train route.",
            "July to March",
            Arrays.asList(
                new PhotoSpot("pp1", "Patalpani Waterfall Cascade", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80", "300-foot sheer drop waterfall creating lush rainbow spray in monsoons.", "6:00 AM - 6:00 PM", "Free"),
                new PhotoSpot("pp2", "Patalpani Heritage Train Railway Bridge", "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=800&q=80", "British era narrow gauge railway line passing over deep gorge bridges.", "9:00 AM - 5:00 PM", "Train ticket ₹30")
            ),
            Arrays.asList(
                new NearbyAttraction("npp1", "Janapav Hill (Birthplace of Parashurama)", 18.0, 100, "Highest hill peak in Malwa region, origin of Chambal river.", "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=600&q=80", "Spiritual")
            ),
            Arrays.asList(
                new HotelOption("hpp1", "Mhow Eco Resort & Stay", 4.2, 2200, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Nature View", "Restaurant", "Parking"))
            )
        ));

        // 19. Mandu
        destinationsMap.put("mandu", new Destination(
            "mandu", "Mandu", "City of Joy & Romance of Baz Bahadur - Rani Roopmati", "Heritage",
            26.0, 65.0, 4.9,
            "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=1200&q=80",
            "Perched on a 2,000-foot high plateau in the Vindhya range, Mandu (Shadiabad - City of Joy) is famous for Jahaz Mahal (Ship Palace), Hindola Mahal, and romantic Afghan architecture.",
            "October to March",
            Arrays.asList(
                new PhotoSpot("md1", "Jahaz Mahal (Ship Palace)", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80", "Floating-style two-storey palace built between two artificial lakes (Kapur and Munj) resembling a ship anchored in water.", "6:00 AM - 6:00 PM", "₹25 / Foreigner ₹300"),
                new PhotoSpot("md2", "Rani Roopmati Pavilion", "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80", "Hilltop pavilion built for Queen Roopmati to gaze upon the sacred Narmada River before breaking her daily fast.", "6:00 AM - 6:30 PM", "Included in Ticket"),
                new PhotoSpot("md3", "Hindola Mahal (Swinging Palace)", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=800&q=80", "Audience hall with sloping walls giving an optical illusion of swinging.", "6:00 AM - 6:00 PM", "Included")
            ),
            Arrays.asList(
                new NearbyAttraction("nmd1", "Jami Masjid Mandu", 2.0, 50, "Inspired by the Great Mosque of Damascus with acoustic echo domes.", "https://images.unsplash.com/photo-1564507592333-c60657eea523?auto=format&fit=crop&w=600&q=80", "Heritage"),
                new NearbyAttraction("nmd2", "Baz Bahadur Palace", 4.0, 100, "16th-century palace blending Afghan and Maratha courtyards.", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=600&q=80", "Heritage")
            ),
            Arrays.asList(
                new HotelOption("hmd1", "MP Tourism Malwa Retreat Mandu", 4.6, 3100, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Valley View", "Restaurant", "Garden Lawn")),
                new HotelOption("hmd2", "MP Tourism Malwa Resort Mandu", 4.5, 2700, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Lake Front", "AC Rooms", "Free WiFi"))
            )
        ));

        // 20. Bandhavgarh National Park
        destinationsMap.put("bandhavgarh", new Destination(
            "bandhavgarh", "Bandhavgarh National Park", "Highest Density Tiger Kingdom & Ancient Fort", "Wildlife",
            82.0, 42.0, 4.9,
            "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=1200&q=80",
            "World famous national park with India's highest tiger density, ancient 2,000-year-old Bandhavgarh Fort, and 35-foot reclining Vishnu statue (Sheesh Shaiya).",
            "October to June",
            Arrays.asList(
                new PhotoSpot("bg1", "Tala Zone Tiger Safari", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80", "Premium safari zone with highest tiger sightings amidst ancient hill cliffs.", "6:00 AM - 11:00 AM / 3:00 PM - 6:00 PM", "₹4,000 per Jeep"),
                new PhotoSpot("bg2", "Sheesh Shaiya Reclining Vishnu", "https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80", "65-foot monolithic 10th-century stone statue of Lord Vishnu sleeping on Sheshnaag in natural forest pool.", "6:00 AM - 11:00 AM", "Safari Permit"),
                new PhotoSpot("bg3", "Bandhavgarh Fort Hill", "https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=800&q=80", "Ancient fort believed to have been given by Lord Ram to brother Lakshmana.", "6:00 AM - 11:00 AM", "Forest Permit")
            ),
            Arrays.asList(
                new NearbyAttraction("nbg1", "Magdhi Zone Safari", 15.0, 3500, "Wildlife zone featuring dense sal forests and grassland waterholes.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                new NearbyAttraction("nbg2", "Khajuraho Day Excursion", 210.0, 2500, "Day excursion connecting wildlife with UNESCO temple heritage.", "https://images.unsplash.com/photo-1626082896492-766af4eb6501?auto=format&fit=crop&w=600&q=80", "Heritage")
            ),
            Arrays.asList(
                new HotelOption("hbg1", "MP Tourism White Tiger Forest Lodge", 4.7, 4200, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", Arrays.asList("Inside Forest Edge", "Swimming Pool", "Safari Help", "Multi-cuisine")),
                new HotelOption("hbg2", "Mahua Kothi Taj Bandhavgarh", 4.9, 15000, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", Arrays.asList("Ultra-Luxury Safari Lodge", "Private Butler", "Naturalist Escort"))
            )
        ));
    }

    public List<Destination> getAllDestinations() {
        return new ArrayList<>(destinationsMap.values());
    }

    public Destination getDestinationById(String id) {
        if (id == null) return destinationsMap.get("ujjain");
        String key = id.toLowerCase().trim();
        return destinationsMap.getOrDefault(key, destinationsMap.get("ujjain"));
    }

    public List<Destination> searchDestinations(String query) {
        if (query == null || query.isBlank()) return getAllDestinations();
        String q = query.toLowerCase().trim();
        List<Destination> result = new ArrayList<>();
        for (Destination d : destinationsMap.values()) {
            if (d.name.toLowerCase().contains(q) ||
                d.tagline.toLowerCase().contains(q) ||
                d.category.toLowerCase().contains(q) ||
                d.description.toLowerCase().contains(q)) {
                result.add(d);
            }
        }
        return result;
    }
}
