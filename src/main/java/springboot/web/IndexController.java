package springboot.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import springboot.config.oauth.dto.SessionUser;
import springboot.domain.user.Role;
import springboot.service.PostsService;
import springboot.web.dto.PostsResponseDto;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;
	private final HttpSession httpSession;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("posts", postsService.findAllDesc());
		
		// 로그인한 사용자(세션 유무)이면 userName을 템플릿으로 전달
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		if (user != null) {
			System.out.println(">>>" + user.getName());
			//여기에서 LoginUserName대신 userName쓰면 사용자계정 이름이 뜬다(HPE떠버림)
			model.addAttribute("LoginUserName", user.getName());
			//model.addAttribute("LoginUserPicture", user.getPicture());
			model.addAttribute("LoginDefault", user.getRole());
			System.out.println(">>>" + user.getName());
			if(user.getRole().name() == "DEFAULT") {
				System.out.println(">>>" + user.getRole());
				user.setRole(Role.USER);
				model.addAttribute("LoginDefault", user.getRole());
				System.out.println(">>>" + user.getRole());
			}
		}
		return "index";    // <== src/main/resources/templates/index.mustache 파일을 반환
	}
	
	@GetMapping("/info")
	public String info(Model model) {
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		model.addAttribute("LoginUserName", user.getName());
		return "info";
	}
	
	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}
	
	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {
		PostsResponseDto dto = postsService.findById(id);
		model.addAttribute("post", dto);
		return "posts-update";
	}
}
