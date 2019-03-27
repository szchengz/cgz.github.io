package annotation;

@TestAnnotation(id=2, msg="ccc")
public class Test1 {

	public static void main(String[] args) {
		System.out.println("Test1");
		
		boolean hasAnnotation = Test1.class.isAnnotationPresent(TestAnnotation.class);
		
		if ( hasAnnotation ) {
			TestAnnotation testAnnotation = Test1.class.getAnnotation(TestAnnotation.class);
			
			System.out.println("id:"+testAnnotation.id());
			System.out.println("msg:"+testAnnotation.msg());
		}
	}

}
