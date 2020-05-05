package springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	//       앞이 키값,    뒤가 타이틀
	DEFAULT("ROLE_DEFAULT", "방문객"),
	GUEST("ROLE_GUEST", "손님"),
	USER("ROLE_USER", "일반사용자");
	
	private final String key;
	private final String title;
}
