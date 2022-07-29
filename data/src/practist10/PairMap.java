package practist10;

abstract class PairMap {
	protected String keyArray[]; // key들을 저장하는 배열
	protected String valueArray[]; // value 들을 저장하는 배열

	abstract String get(String key); // key값으로 value를 검색

	abstract void put(String key, String value); // key값을 가진 아이템(Value와 함께)을 삭제//삭제된 value 리턴
	abstract int length();
}
