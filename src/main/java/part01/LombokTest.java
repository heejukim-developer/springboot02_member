package part01;

public class LombokTest {
	public static void main(String[] args) {
		MemberVO mv = new MemberVO();
		mv.setName("홍길동");
		mv.setAge(30);
		mv.setLoc("서울");
	
		System.out.printf("%s %d %s\n" ,mv.getName(), mv.getAge(), mv.getLoc());
		System.out.println(mv.toString());
		
		mv = new MemberVO("여진구" , 28 ,"경기");
		System.out.printf("%s %d %s\n" ,mv.getName(), mv.getAge(), mv.getLoc());
		System.out.println(mv.toString());
		
		
	}
	
}
