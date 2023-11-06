package neowolf.openai;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;

@SessionAttributes({ "currentChat" })
@Controller
@RequestMapping("/api/V1")
@CrossOrigin(origins = { "http://localhost:8081",  "http://localhost:8080" })
public class ChatGPTController {
	private static final Logger log = LoggerFactory.getLogger(ChatGPTController.class);

	private final static Locale locale = Locale.getDefault();

	@Autowired
	private ChatGPTService chatGPTService;

	@Autowired
	private MessageSource messageSource;

	/*@GetMapping("/")
	public String index(ChatGPTBean chatGPTBean, Model model, SessionStatus status) {
		chatGPTBean.setTitle(messageSource.getMessage("homeText", null, locale).split("Q:")[0]);
		model.addAttribute("chatGPTBean", chatGPTBean);
		status.setComplete();

		return "index";
	}*/
	
	
	@ResponseBody
	@GetMapping("/")
	public ResponseEntity<?> index(ChatGPTBean chatGPTBean, Model model, SessionStatus status) {
		chatGPTBean.setTitle(messageSource.getMessage("homeText", null, locale).split("Q:")[0]);
		model.addAttribute("chatGPTBean", chatGPTBean);
		status.setComplete();
		return new ResponseEntity<>(chatGPTBean, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping("http://localhost:8081/{prompt}")
	public String chat(@PathVariable String prompt, ChatGPTBean chatGPTBean, Model model, HttpSession session) {
		log.info("Getting answer from Open AI ChatGPT");
		chatGPTBean.setTitle(messageSource.getMessage("homeText", null, locale).split("Q:")[0]);
		chatGPTBean.setPrompt(prompt);
		String response = "Fatal Error";
		try {
			String currentChat = (String) session.getAttribute("currentChat");
			currentChat = (currentChat == null || "".equals(currentChat) ? "" : currentChat) + "\nQ: " + prompt
					+ "\nA: ";
			currentChat += chatGPTService.parseUnstructuredData(prompt).trim();
			response = currentChat;
			model.addAttribute("currentChat", currentChat);
		} catch (Exception e) {
			e.printStackTrace();
			response = messageSource.getMessage("apiCallfailed", new String[] { e.getMessage() }, locale);
		}

		return response.trim();
	}
}
