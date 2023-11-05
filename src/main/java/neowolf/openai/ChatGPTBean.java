package neowolf.openai;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestMapping;

public class ChatGPTBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String prompt;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public String toString() {
		return "ChatGPTBean [title=" + title + ", prompt=" + prompt + "]";
	}

}
