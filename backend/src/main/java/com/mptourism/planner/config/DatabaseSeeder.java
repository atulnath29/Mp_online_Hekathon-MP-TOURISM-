package com.mptourism.planner.config;

import com.mptourism.planner.model.Hotel;
import com.mptourism.planner.model.Location;
import com.mptourism.planner.model.NearbyPlace;
import com.mptourism.planner.model.User;
import com.mptourism.planner.repository.LocationRepository;
import com.mptourism.planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedDemoUser();

        if (locationRepository.count() > 0) {
            System.out.println(">>> DatabaseSeeder: Locations already seeded. Count = " + locationRepository.count() + ". Skipping seeding.");
            return;
        }

        System.out.println(">>> DatabaseSeeder: Seeding 20 MP Tourist Locations with Nearby Places and Hotels...");

        List<Location> locations = Arrays.asList(
            // 1. Ujjain
            createLocation(
                "Ujjain",
                "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80",
                "Ujjain is one of Hinduism's seven sacred cities (Sapta Puri) on the Shipra River, famous for Mahakaleshwar Jyotirlinga and the grand Kumbh Mela.",
                23.1765, 75.7885, "Spiritual", 9.8, "City of Mahakal & Sacred Shipra River", "October to March",
                Arrays.asList(
                    new NearbyPlace("Mangalnath Mandir", 5.5, 150.0, "Birthplace of planet Mars as per Vedic astrology.", "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=600&q=80", "Spiritual"),
                    new NearbyPlace("ISKCON Ujjain", 3.2, 100.0, "Stunning white marble temple complex with serene vegetarian dining.", "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=600&q=80", "Culture"),
                    new NearbyPlace("Kaliadeh Palace", 8.0, 200.0, "Historic water palace built on an island in Shipra River in 1458 AD.", "https://images.unsplash.com/photo-1513694203232-719a280e022f?auto=format&fit=crop&w=600&q=80", "Heritage"),
                    new NearbyPlace("Vedh Shala Observatory", 2.8, 80.0, "Astronomical observatory built by Sawai Jai Singh II in 1725 AD.", "https://images.unsplash.com/photo-1507676184212-d03ab07a01bf?auto=format&fit=crop&w=600&q=80", "Heritage")
                ),
                Arrays.asList(
                    new Hotel("Hotel Avantika Grand", 9.4, 3500.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Free WiFi, Breakfast Included, Near Mahakal Temple"),
                    new Hotel("MP Tourism Resort Shipra", 9.0, 2800.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "River View, Restaurant, Garden, Parking"),
                    new Hotel("Mahakal Heritage Stay", 8.4, 1800.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Budget Friendly, AC Rooms, 24h Service"),
                    new Hotel("Shipra Valley Budget Lodge", 8.0, 950.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Clean Rooms, Hot Water, Central Location")
                )
            ),

            // 2. Gwalior
            createLocation(
                "Gwalior",
                "https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=800&q=80",
                "Dominating the skyline with its 8th-century hill fortress, Gwalior is celebrated for Scindia royal palaces and music legacy of Tansen.",
                26.2183, 78.1828, "Heritage", 9.6, "City of Forts, Royalty & Classical Music", "October to March",
                Arrays.asList(
                    new NearbyPlace("Tighra Dam", 23.0, 300.0, "Freshwater dam featuring speed boating and water sports.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Bateshwar Temples", 35.0, 450.0, "Group of 200 sandstone Hindu temples restored inside Morena forest.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage"),
                    new NearbyPlace("Mitawali Padavali Temple", 40.0, 400.0, "Circular temple that inspired the architectural design of Parliament House.", "https://images.unsplash.com/photo-1564507592333-c60657eea523?auto=format&fit=crop&w=600&q=80", "Heritage")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Tansen Residency", 8.8, 3200.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Central Location, Restaurant, WiFi, Bar"),
                    new Hotel("Fort View Regency", 8.4, 2200.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Fort Facing, Room Service, Free Parking"),
                    new Hotel("Scindia Heritage Inn", 8.2, 1600.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "AC Rooms, Travel Desk, Restaurant"),
                    new Hotel("Gwalior City Lodge", 7.8, 900.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Railway Station, Budget Stay")
                )
            ),

            // 3. Khajuraho
            createLocation(
                "Khajuraho",
                "https://images.unsplash.com/photo-1626082896492-766af4eb6501?auto=format&fit=crop&w=800&q=80",
                "World famous UNESCO World Heritage site renowned for 10th-century Chandela temples celebrating life, spirituality, and art in sandstone.",
                24.8318, 79.9199, "Heritage", 9.8, "UNESCO World Heritage Temple Architecture", "October to March",
                Arrays.asList(
                    new NearbyPlace("Panna National Park Safari", 25.0, 2500.0, "Tiger sanctuary along Ken river with scenic gorges.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                    new NearbyPlace("Pandav Falls", 30.0, 150.0, "Natural waterfall creating a clear green lake legendarily visited by Pandavas.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Raneh Falls & Canyon", 20.0, 200.0, "30-meter deep gorge of multi-colored crystalline granite on Ken River.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature")
                ),
                Arrays.asList(
                    new Hotel("Hotel Chandela Khajuraho", 8.8, 3800.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Lush Gardens, Swimming Pool, Restaurant"),
                    new Hotel("MP Tourism Payal Khajuraho", 8.4, 2400.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Near Western Temples, Garden Lawn"),
                    new Hotel("Temple View Inn", 8.0, 1400.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Walking Distance to Temples, WiFi"),
                    new Hotel("Khajuraho Traveler Lodge", 7.6, 850.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Backpacker Friendly, Hot Showers")
                )
            ),

            // 4. Sanchi
            createLocation(
                "Sanchi",
                "https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=800&q=80",
                "UNESCO World Heritage Buddhist hub commissioned by Emperor Ashoka in 3rd century BCE with preserved carved Torana gateways.",
                23.4873, 77.7418, "Spiritual", 9.6, "Cradle of Buddhist Art & Emperor Ashoka's Stupa", "October to March",
                Arrays.asList(
                    new NearbyPlace("Udayagiri Caves", 12.0, 120.0, "20 rock-cut caves from Gupta period featuring iconic Varaha panel.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage"),
                    new NearbyPlace("Heliodorus Pillar", 18.0, 50.0, "Monolithic stone pillar erected around 110 BCE by Greek envoy Heliodorus.", "https://images.unsplash.com/photo-1564507592333-c60657eea523?auto=format&fit=crop&w=600&q=80", "Heritage")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Gateway Retreat Sanchi", 9.0, 3000.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Hill View, Restaurant, AC Rooms, Serene Ambience"),
                    new Hotel("Stupa View Guest House", 8.2, 1500.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Stupa Complex, Home Cooked Meals"),
                    new Hotel("Ashoka Heritage Lodge", 7.8, 1100.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Clean Rooms, Hot Water, Parking")
                )
            ),

            // 5. Pachmarhi
            createLocation(
                "Pachmarhi",
                "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80",
                "Madhya Pradesh's only hill station at 1,067m in Satpura range with pine forests, waterfalls, caves, and Dhoopgarh peak.",
                22.4674, 78.4346, "Nature", 9.8, "Queen of Satpura Hill Station", "October to June",
                Arrays.asList(
                    new NearbyPlace("Satpura Tiger Reserve", 15.0, 3000.0, "Jeep, canoe, and walking safaris deep in tiger country.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                    new NearbyPlace("Apsara Vihar", 3.0, 50.0, "Gentle waterfall forming a natural swimming pool.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Bee Falls", 2.5, 100.0, "Spectacular 35m waterfall surrounded by green hills.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Waterfall")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Champak Bungalow", 8.8, 2900.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Lush Garden, Family Rooms, Restaurant"),
                    new Hotel("Satpura Hill Retreat", 8.6, 2200.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Valley View, Bonfire, Restaurant"),
                    new Hotel("Pine View Hotel", 8.0, 1500.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Pine Forest Proximity, Hot Shower"),
                    new Hotel("Pachmarhi Valley Lodge", 7.6, 950.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Economical Stay, Tour Assistance")
                )
            ),

            // 6. Kanha National Park
            createLocation(
                "Kanha National Park",
                "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80",
                "Inspiration for 'The Jungle Book', Kanha is famous for Royal Bengal Tigers, rare Barasingha, and dense Sal forests.",
                22.3345, 80.6115, "Wildlife", 9.8, "The Original Jungle Book Sanctuary", "October to June",
                Arrays.asList(
                    new NearbyPlace("Mukki Gate Zone Safari", 20.0, 3500.0, "Renowned for high tiger sighting frequency and open grasslands.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                    new NearbyPlace("Kawardha Palace Visit", 75.0, 500.0, "Heritage palace built by Maharaja Dharamraj Singh in marble.", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=600&q=80", "Heritage")
                ),
                Arrays.asList(
                    new Hotel("Baghira Jungle Resort Kanha", 9.2, 4500.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Inside Forest, Swimming Pool, Safari Booking, Meals"),
                    new Hotel("Wild Chalet Resort", 8.8, 3600.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "River Bank View, Cottage Rooms, Nature Walk"),
                    new Hotel("Sal Valley Resort", 8.4, 2400.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Eco-friendly, Campfire, Restaurant"),
                    new Hotel("Kanha Safari Lodge", 8.0, 1600.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Khatia Gate, Budget Safari Lodge")
                )
            ),

            // 7. Bhopal
            createLocation(
                "Bhopal",
                "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=800&q=80",
                "Capital of MP blending 11th-century Upper Lake with grand Begum mosques and world-class tribal museums.",
                23.2599, 77.4126, "Culture", 9.4, "City of Lakes & Regal Begum Heritage", "October to March",
                Arrays.asList(
                    new NearbyPlace("Bhimbetka Rock Shelters", 45.0, 200.0, "UNESCO World Heritage Paleolithic rock paintings over 30,000 years old.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage"),
                    new NearbyPlace("Bhojpur Shiv Mandir", 28.0, 150.0, "Massive monolithic 18-foot Shiva Lingam in an 11th-century temple.", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=600&q=80", "Spiritual"),
                    new NearbyPlace("Kerwa Dam", 15.0, 250.0, "Eco-tourism spot with zip-line and waterside dining.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Adventure")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Palash Residency", 8.6, 2600.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "City Center, WiFi, Restaurant, Clean Rooms"),
                    new Hotel("Lake View Residency", 8.4, 2100.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Overlooking Bhojtal Lake, Room Service"),
                    new Hotel("Bhopal Central Stay", 8.0, 1400.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near MP Nagar, AC Rooms, WiFi"),
                    new Hotel("Bhojpal Inn", 7.6, 950.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Economical Stay, Hot Water")
                )
            ),

            // 8. Indore
            createLocation(
                "Indore",
                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=800&q=80",
                "Cleanest city in India, Indore combines Holkar royal elegance with night food markets like Sarafa and Chappan Dukan.",
                22.7196, 75.8577, "Culture", 9.8, "Commercial Capital & Street Food Paradise", "October to March",
                Arrays.asList(
                    new NearbyPlace("Ralamandal Wildlife Sanctuary", 12.0, 100.0, "Oldest wildlife sanctuary in MP with trekking paths.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Tincha Falls", 25.0, 150.0, "300-foot monsoon waterfall nestled in lush greenery.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Waterfall")
                ),
                Arrays.asList(
                    new Hotel("Lemon Tree Hotel Indore", 8.8, 3200.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Centrally Located, Fitness Center, WiFi"),
                    new Hotel("Hotel Crown Palace", 8.4, 2400.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Near South Tukoganj, Multi-cuisine Dining"),
                    new Hotel("Sarafa Heritage Hotel", 8.0, 1600.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Rajwada & Sarafa, AC Rooms"),
                    new Hotel("Malwa Inn Indore", 7.8, 1000.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Bus Stand, Clean Rooms")
                )
            ),

            // 9. Orchha
            createLocation(
                "Orchha",
                "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80",
                "Founded in 16th century by Rudra Pratap, Orchha freezes time with fort palaces, river chhatris, and Ram Raja Temple.",
                25.3506, 78.6433, "Heritage", 9.6, "The Hidden Gem on Betwa River", "October to March",
                Arrays.asList(
                    new NearbyPlace("Betwa River Rafting", 1.5, 800.0, "River rafting thrill along rocky river rapids.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Adventure"),
                    new NearbyPlace("Orchha Wildlife Sanctuary", 3.0, 150.0, "Nature reserve with deer, blue bulls, and river birds.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Betwa Retreat", 9.2, 3400.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "River View, Heritage Cottages, Pool, Restaurant"),
                    new Hotel("Bundelkhand Riverside", 8.8, 2800.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Betwa Bank, Swimming Pool, Royal Suites"),
                    new Hotel("Orchha Palace View", 8.2, 1700.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Jahangir Mahal, Rooftop Restaurant"),
                    new Hotel("Betwa Homestay", 7.8, 950.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Friendly Host, Village Vibe, Clean Bed")
                )
            ),

            // 10. Omkareshwar
            createLocation(
                "Omkareshwar",
                "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80",
                "Sacred island shaped like 'OM' in Narmada River, housing Shiva Jyotirlinga and 108ft Statue of Oneness.",
                22.2464, 76.1511, "Spiritual", 9.6, "Island Jyotirlinga Shaped Like 'OM'", "October to March",
                Arrays.asList(
                    new NearbyPlace("Narmada Parikrama Bridge", 1.0, 50.0, "Suspension bridge offering panoramic river views.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Spiritual"),
                    new NearbyPlace("Kaveri Sangam Ghat", 3.0, 100.0, "Confluence of Kaveri and Narmada rivers for ritual dips.", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=600&q=80", "Spiritual")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Narmada Resort Omkareshwar", 8.8, 2800.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "River Facing, Restaurant, Clean AC Rooms"),
                    new Hotel("Omkareshwar Temple View Inn", 8.2, 1800.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Main Temple, Vegetarian Dining"),
                    new Hotel("Holy Narmada Guest House", 7.8, 1100.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Ghat Proximity, Hot Water, Budget Stay")
                )
            ),

            // 11. Jabalpur
            createLocation(
                "Jabalpur",
                "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80",
                "Flanked by Narmada River, Jabalpur is famous for white marble rock gorges of Bhedaghat and Dhuandhar waterfall.",
                23.1815, 79.9864, "Nature", 9.6, "City of Marble Rocks & Roaring Waterfalls", "October to April",
                Arrays.asList(
                    new NearbyPlace("Bargi Dam Cruise", 40.0, 350.0, "Reservoir offering motor boat cruises and eco-dining.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Dumna Nature Reserve", 10.0, 100.0, "Eco-park with deer, canopy walk, and kayaking.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Nature")
                ),
                Arrays.asList(
                    new Hotel("Hotel Kalchuri Grand", 8.6, 2700.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Near Railway Station, WiFi, Restaurant"),
                    new Hotel("Narmada View Residency", 8.2, 1900.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "River Proximity, AC Rooms, Parking"),
                    new Hotel("Jabalpur City Hotel", 7.8, 1200.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Budget Business Stay, 24h Desk")
                )
            ),

            // 12. Bhedaghat
            createLocation(
                "Bhedaghat",
                "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80",
                "Famous for 100-ft white marble rock canyons soaring over Narmada River waters near Jabalpur.",
                23.1311, 79.8005, "Nature", 9.8, "Magical Marble Canyons & Cable Car Ride", "November to May",
                Arrays.asList(
                    new NearbyPlace("Soapstone Handicrafts Market", 0.5, 100.0, "Local market selling carved marble statues and keychains.", "https://images.unsplash.com/photo-1566127444979-b3d2b654e3d7?auto=format&fit=crop&w=600&q=80", "Shopping"),
                    new NearbyPlace("Chausath Yogini Temple", 1.2, 50.0, "10th-century circular hilltop temple housing 64 yogini statues.", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=600&q=80", "Heritage")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Motel Marble Rocks", 8.8, 2900.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Gorge View, Restaurant, Garden"),
                    new Hotel("Bhedaghat River View Lodge", 8.2, 1700.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Dhuandhar Falls, Hot Water"),
                    new Hotel("Dhuandhar Tourist Home", 7.8, 1050.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Clean Rooms, Economical Stay")
                )
            ),

            // 13. Maheshwar
            createLocation(
                "Maheshwar",
                "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=800&q=80",
                "Capital of saintly Queen Ahilyabai Holkar on Narmada, famous for stone ghats, fort, and Maheshwari silk sarees.",
                22.1764, 75.5847, "Heritage", 9.6, "Ahilya Fort & Handloom Weaving Center", "October to March",
                Arrays.asList(
                    new NearbyPlace("Narmada Sunset Boat Cruise", 0.5, 150.0, "Sunset wooden boat ride floating lighted diyas.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Sahastradhara Waterfall", 5.0, 200.0, "Narmada river breaking into a thousand streams over rocks.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Waterfall")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Narmada Retreat Maheshwar", 9.0, 3100.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "River Frontage, Lawns, Restaurant"),
                    new Hotel("Ahilya Fort Heritage Stay", 8.6, 2500.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Heritage Experience, Courtyard View"),
                    new Hotel("Holkar Palace Guest House", 8.0, 1400.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Ghat Proximity, Clean AC Rooms")
                )
            ),

            // 14. Shivpuri
            createLocation(
                "Shivpuri",
                "https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=800&q=80",
                "Summer capital of Scindias surrounded by Madhav National Park forests, marble cenotaphs, and lakes.",
                25.4320, 77.6593, "Heritage", 9.2, "Royal Summer Capital & Scindia Chhatris", "October to March",
                Arrays.asList(
                    new NearbyPlace("Bhadaiya Kund", 4.0, 50.0, "Natural mineral spring believed to have medicinal properties.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Narwar Fort", 42.0, 300.0, "Historic hilltop fort associated with Raja Nala & Damayanti.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Tourist Village Shivpuri", 8.6, 2500.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Lakeside Cottages, Restaurant, Park Views"),
                    new Hotel("Madhav Forest Lodge", 8.0, 1600.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near National Park, Peaceful"),
                    new Hotel("Shivpuri City Hotel", 7.6, 950.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Economical Stay, Hot Water")
                )
            ),

            // 15. Satna
            createLocation(
                "Satna",
                "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80",
                "Gateway to Maihar Sharda Devi hilltop shrine and ancient Bharhut Buddhist stupa relics.",
                24.6005, 80.8322, "Spiritual", 9.2, "Maihar Shardadevi & Ancient Bharhut Buddhist Relics", "October to March",
                Arrays.asList(
                    new NearbyPlace("Chitrakoot Dham Tour", 75.0, 600.0, "Holy town where Lord Ram spent 11 years of exile.", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=600&q=80", "Spiritual"),
                    new NearbyPlace("Maihar Temple Ropeway", 40.0, 140.0, "1063-step hilltop temple dedicated to Goddess Sharda.", "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=600&q=80", "Spiritual")
                ),
                Arrays.asList(
                    new Hotel("Hotel Bharhut Satna", 8.4, 2200.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Near Station, WiFi, Restaurant"),
                    new Hotel("Satna City Inn", 7.8, 1300.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "AC Rooms, Parking"),
                    new Hotel("Maihar Devi Lodge", 7.6, 900.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Budget Pilgrim Stay")
                )
            ),

            // 16. Bawangaja
            createLocation(
                "Bawangaja",
                "https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80",
                "Jain pilgrim site in Barwani featuring an 84-foot monolithic statue of Lord Adinath carved in rock.",
                22.0392, 74.8726, "Spiritual", 9.4, "Colossal 84-Ft Monolithic Adinath Jain Statue", "October to March",
                Arrays.asList(
                    new NearbyPlace("Narmada River View Barwani", 15.0, 100.0, "Serene river banks with mountain backdrops.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Chulgiri Hill Temples", 2.0, 50.0, "Complex of 11 hill temples on Satpura peak.", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=600&q=80", "Spiritual")
                ),
                Arrays.asList(
                    new Hotel("Bawangaja Jain Guest House", 8.6, 1500.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Pure Vegetarian, Clean Rooms, Parking"),
                    new Hotel("Satpura Pilgrim Lodge", 8.0, 1100.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Hot Water, Peaceful Ambience"),
                    new Hotel("Barwani Tourist Hotel", 7.6, 850.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Budget Friendly, Clean Beds")
                )
            ),

            // 17. Mohadi Falls
            createLocation(
                "Mohadi Falls",
                "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80",
                "Unspoiled gorge waterfall 30km from Indore cascading down green grassy valleys.",
                22.5642, 75.9184, "Waterfall", 9.2, "Unspoiled Gorge Waterfall & Nature Trek", "July to February",
                Arrays.asList(
                    new NearbyPlace("Choral Dam Eco Spot", 12.0, 150.0, "Quiet dam reservoir with speed boat rides.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature"),
                    new NearbyPlace("Bamniya Kund Waterfall", 18.0, 100.0, "High waterfall with natural cliff pool.", "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=600&q=80", "Waterfall")
                ),
                Arrays.asList(
                    new Hotel("Mohadi Nature Resort", 8.4, 2200.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Valley View, Trekking Help, Campfire"),
                    new Hotel("Choral Dam Eco Lodge", 8.0, 1600.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Lakeside View, Restaurant"),
                    new Hotel("Valley Trekker Lodge", 7.6, 950.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Economical Stay, Hot Showers")
                )
            ),

            // 18. Patalpani Waterfall
            createLocation(
                "Patalpani Waterfall",
                "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80",
                "Famous 300-ft monsoon waterfall near Mhow with historic heritage railway line crossing green ravines.",
                22.5186, 75.7850, "Waterfall", 9.4, "Scenic 300-Ft Waterfall & Heritage Railway Track", "July to March",
                Arrays.asList(
                    new NearbyPlace("Mhow Cantonment Heritage Walk", 8.0, 100.0, "Colonial military town history & birthplace of Dr. Ambedkar.", "https://images.unsplash.com/photo-1548625361-185d263b6555?auto=format&fit=crop&w=600&q=80", "Heritage"),
                    new NearbyPlace("Janapav Kuti", 20.0, 150.0, "High hill peak, birthplace of Lord Parashurama.", "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=600&q=80", "Spiritual")
                ),
                Arrays.asList(
                    new Hotel("Heritage Railway Resort Mhow", 8.6, 2400.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Valley Facing, Restaurant, WiFi"),
                    new Hotel("Patalpani Valley Inn", 8.0, 1500.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Near Waterfall Viewpoint, Parking"),
                    new Hotel("Mhow Cantonment Lodge", 7.6, 900.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Budget Friendly, Clean Rooms")
                )
            ),

            // 19. Bandhavgarh National Park
            createLocation(
                "Bandhavgarh National Park",
                "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80",
                "Highest density of Royal Bengal Tigers in India, centered around an ancient 2000-year-old fort.",
                23.7014, 81.0261, "Wildlife", 9.8, "Land of the Royal Bengal Tiger", "October to June",
                Arrays.asList(
                    new NearbyPlace("Tala Zone Safari", 5.0, 3500.0, "Prime zone for tiger sighting and fort hill views.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                    new NearbyPlace("Shesh Shaiya Statue", 8.0, 200.0, "65-foot reclining Vishnu statue carved in rock inside forest.", "https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=600&q=80", "Spiritual")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism White Tiger Forest Lodge", 9.0, 4200.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Lakeside Jungle Lodge, Pool, Safari Desk, Dining"),
                    new Hotel("Bandhavgarh Tiger Resort", 8.6, 3200.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Near Tala Gate, Cottage Stay, Restaurant"),
                    new Hotel("Jungle Inn Bandhavgarh", 8.0, 1800.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "AC Rooms, Campfire, Naturalist Guide"),
                    new Hotel("Sal Forest Eco Lodge", 7.8, 1100.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Budget Forest Stay, Home Meals")
                )
            ),

            // 20. Pench National Park
            createLocation(
                "Pench National Park",
                "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80",
                "Mowgli's land straddling MP and Maharashtra, home to Tigers, Leopards, and teak forests.",
                21.6558, 79.3005, "Wildlife", 9.6, "The Real Mowgli Land", "October to June",
                Arrays.asList(
                    new NearbyPlace("Turia Gate Safari", 2.0, 3500.0, "Most popular safari gate for tiger & leopard sightings.", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=600&q=80", "Wildlife"),
                    new NearbyPlace("Totladoh Dam", 15.0, 200.0, "Huge reservoir inside forest with migratory bird watching.", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80", "Nature")
                ),
                Arrays.asList(
                    new Hotel("MP Tourism Kipling Court Pench", 9.0, 3800.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80", "Near Turia Gate, Swimming Pool, Safari Desk"),
                    new Hotel("Mowgli Jungle Resort", 8.6, 2800.0, "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80", "Teak Wood Cottages, Cultural Dance, Meals"),
                    new Hotel("Pench Tiger Camp", 8.2, 1900.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Tented Accommodation, Campfire"),
                    new Hotel("Teak Wood Retreat", 7.8, 1200.0, "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80", "Economical Wildlife Lodge")
                )
            )
        );

        locationRepository.saveAll(locations);
        System.out.println(">>> DatabaseSeeder: Successfully seeded " + locations.size() + " MP Tourist Locations!");
    }

    private Location createLocation(String name, String imageUrl, String shortDescription, Double lat, Double lon, String category, Double rating, String tagline, String bestTime, List<NearbyPlace> nearbyPlaces, List<Hotel> hotels) {
        Location loc = new Location(name, imageUrl, shortDescription, lat, lon, category, rating, tagline, bestTime);
        for (NearbyPlace np : nearbyPlaces) {
            loc.addNearbyPlace(np);
        }
        for (Hotel h : hotels) {
            loc.addHotel(h);
        }
        return loc;
    }

    private void seedDemoUser() {
        if (!userRepository.existsByEmail("demo@mptourism.com")) {
            User demoUser = new User(
                "Demo",
                "User",
                "demo@mptourism.com",
                "9876543210",
                passwordEncoder.encode("Demo@123"),
                null
            );
            userRepository.save(demoUser);
            System.out.println(">>> DatabaseSeeder: Seeded demo user demo@mptourism.com");
        } else {
            System.out.println(">>> DatabaseSeeder: Demo user demo@mptourism.com already exists.");
        }
    }
}

