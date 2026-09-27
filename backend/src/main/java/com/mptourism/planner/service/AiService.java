package com.mptourism.planner.service;

import com.mptourism.planner.dto.ChatRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiService {

    @Value("${ai.llm.api-key:DEMO_MP_TOURISM_AI_KEY}")
    private String apiKey;

    @Value("${ai.llm.api-url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateResponse(ChatRequest request) {
        String msg = request.getMessage() != null ? request.getMessage().trim() : "";
        String place = request.getSelectedPlace() != null ? request.getSelectedPlace() : "Madhya Pradesh";
        Double budget = request.getBudget();
        Integer days = request.getDays();
        Integer travelers = request.getTravelers();
        String page = request.getCurrentPage() != null ? request.getCurrentPage() : "Home";

        // Try calling external LLM API if valid API KEY is set
        if (apiKey != null && !apiKey.startsWith("DEMO_") && !apiKey.isBlank()) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(apiKey);

                Map<String, Object> body = new HashMap<>();
                body.put("model", "gpt-3.5-turbo");
                
                List<Map<String, String>> messages = new ArrayList<>();
                Map<String, String> systemMsg = new HashMap<>();
                systemMsg.put("role", "system");
                systemMsg.put("content", "You are the official Madhya Pradesh Tourism AI Guide assistant. " +
                        "Help tourists with detailed, accurate, polite travel advice for MP. " +
                        "User is currently on Page: " + page + ", viewing Place: " + place +
                        ", Budget: ₹" + (budget != null ? budget : "Not set") +
                        ", Days: " + (days != null ? days : 1) + ", Travelers: " + (travelers != null ? travelers : 1));
                messages.add(systemMsg);

                Map<String, String> userMsg = new HashMap<>();
                userMsg.put("role", "user");
                userMsg.put("content", msg);
                messages.add(userMsg);

                body.put("messages", messages);

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
                ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    List choices = (List) response.getBody().get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map firstChoice = (Map) choices.get(0);
                        Map messageMap = (Map) firstChoice.get("message");
                        if (messageMap != null && messageMap.get("content") != null) {
                            return (String) messageMap.get("content");
                        }
                    }
                }
            } catch (Exception e) {
                // Fallback to domain AI engine if API call fails
            }
        }

        // Domain-specific MP Tourism AI Assistant Engine
        return generateDomainResponse(msg, place, budget, days, travelers, page);
    }

    private String generateDomainResponse(String msg, String place, Double budget, Integer days, Integer travelers, String page) {
        String lower = msg.toLowerCase();

        if (lower.contains("food") || lower.contains("eat") || lower.contains("dish") || lower.contains("restaurant") || lower.contains("snack")) {
            return getFoodAdvice(place);
        } else if (lower.contains("budget") || lower.contains("cost") || lower.contains("save") || lower.contains("cheap") || lower.contains("money")) {
            return getBudgetAdvice(place, budget, days, travelers);
        } else if (lower.contains("best time") || lower.contains("season") || lower.contains("weather") || lower.contains("when to visit")) {
            return "The ideal time to visit **" + place + "** and most of Madhya Pradesh is from **October to March** when temperatures are pleasant (15°C - 25°C), making sightseeing, safari drives, and temple visits very enjoyable!";
        } else if (lower.contains("hotel") || lower.contains("stay") || lower.contains("resort") || lower.contains("lodge")) {
            return "For **" + place + "**, we recommend staying at official **MP Tourism Resorts (MPT)** (like MPT Betwa Retreat, MPT Gateway, or MPT Shipra) or trusted heritage homestays. Check Page 5 for curated hotel options matching your ₹" + (budget != null ? Math.round(budget) : 5000) + " budget!";
        } else if (lower.contains("temple") || lower.contains("aarti") || lower.contains("timing") || lower.contains("time")) {
            return getTimingAdvice(place);
        } else if (lower.contains("itinerary") || lower.contains("plan") || lower.contains("schedule") || lower.contains("day")) {
            return "I can help you build your perfect " + (days != null ? days : 3) + "-day itinerary for **" + place + "**! On Page 4, choose your favourite attractions, and Page 5 will auto-generate your complete morning-to-night daywise plan!";
        } else if (lower.contains("hello") || lower.contains("hi") || lower.contains("hey") || lower.contains("namaste")) {
            return "Namaste! 🙏 I am your **Madhya Pradesh AI Travel Assistant**. I see you are exploring **" + place + "**" + (budget != null ? " with a budget of ₹" + Math.round(budget) : "") + ". How can I help you plan your journey today?";
        } else {
            return "Great query! For **" + place + "**, Madhya Pradesh offers rich cultural heritage, sacred temples, magnificent waterfalls, and wildlife safaris. " +
                   "You can easily customize your trip budget (Page 3), select nearby spots (Page 4), and get hotel recommendations (Page 5). " +
                   "Feel free to ask me about local food, temple timings, or budget tips!";
        }
    }

    private String getFoodAdvice(String place) {
        switch (place.toLowerCase()) {
            case "indore":
                return "🌭 **Indore Food Guide**: Try **Poha Jalebi** in the morning at Chappan Dukan, **Bhutte Ka Kees** (grated spiced corn) and **Garadu** (crispy yam) at Sarafa Night Market, and famous **Jaleba** (giant jalebi)!";
            case "ujjain":
                return "🍲 **Ujjain Food Guide**: Do not miss **Poha Jalebi**, **Sabudana Khichdi**, crisp **Kachori-Samosa** near Mahakal temple, and refreshing **Rabri Glass** at Ram Ghat!";
            case "bhopal":
                return "🍢 **Bhopal Food Guide**: Famous for **Bhopali Poha**, **Suleimani Tea**, **Mawa Bati**, and iconic Chatori Gali delicacies like spicy kebabs and Biryani!";
            case "gwalior":
                return "🥟 **Gwalior Food Guide**: Taste famous **Bedai Poori with Aloo Sabzi**, **Kachoris**, and sweet **Petha** near Naya Bazaar!";
            default:
                return "🍲 **MP Culinary Specialities**: Sample **Dal Bafla** with ghee and churma, **Poha Jalebi**, **Bhutte Ka Kees**, and fresh sugarcane juice across all MP destinations!";
        }
    }

    private String getBudgetAdvice(String place, Double budget, Integer days, Integer travelers) {
        if (budget == null) {
            return "💡 **Budget Tip**: Set your budget on Page 3! A budget of ₹3,000 - ₹5,000 per person per day is ideal for a comfortable MP trip including accommodation, transport, and temple/safari tickets.";
        }
        double dailyBudget = budget / (days != null && days > 0 ? days : 1);
        return "💡 **Budget Breakdown for " + place + "**:\n" +
               "- Total Budget: ₹" + Math.round(budget) + " for " + (days != null ? days : 1) + " days\n" +
               "- Daily Available: ₹" + Math.round(dailyBudget) + "\n" +
               "- **Savings Tip**: Book MP Tourism hotels in advance, use shared cabs for nearby waterfalls/attractions, and dine at authentic local thali places!";
    }

    private String getTimingAdvice(String place) {
        switch (place.toLowerCase()) {
            case "ujjain":
                return "🕉️ **Ujjain Timings**: Mahakaleshwar Temple Bhasma Aarti is at 4:00 AM (prior booking required). General darshan is open 6:00 AM - 11:00 PM. Ram Ghat Aarti starts at 6:30 PM evening.";
            case "gwalior":
                return "🏰 **Gwalior Fort Timings**: Fort is open 6:00 AM - 6:00 PM. Sound & Light Show in evening at 7:30 PM.";
            case "khajuraho":
                return "🏛️ **Khajuraho Temple Timings**: Temple complex open sunrise to sunset (6:00 AM - 6:00 PM). Light and Sound show starts at 6:30 PM (English) & 7:30 PM (Hindi).";
            default:
                return "⏰ Most heritage sites & temples in MP are open from 6:00 AM to 7:00 PM. Evening Aartis usually happen between 6:00 PM - 7:30 PM.";
        }
    }
}
