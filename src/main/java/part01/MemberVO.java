package part01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//롬복 
//@RequiredArgsConstructor //final, @NonNull인 멤버변수 값만 파라미터로 받는 생성 
//@EqualsAndHashCode 
//@ToString 
@NoArgsConstructor //파라미터가 없는 생성자를 생성 
@AllArgsConstructor  //모든 멤버변수 값을 파라미터로 받는 생성자를 생성 
//@Setter
//@Getter
@Data
public class MemberVO {
	private String name;
	private int age;
	private String loc;
}
