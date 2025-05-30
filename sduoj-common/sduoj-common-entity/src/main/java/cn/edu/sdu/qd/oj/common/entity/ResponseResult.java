@Getter
@ToString
public class ResponseResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private int code;
	private String message;
	private long timestamp;
	private T data;  // 修改为泛型类型

	private ResponseResult() {
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * 成功响应，但无响应数据。
	 */
	public static ResponseResult<Void> ok() {
		return ok(null);
	}

	/**
	 * 成功响应，存在响应数据。
	 */
	public static <T> ResponseResult<T> ok(T data) {
		return new ResponseResult<T>()
				.setCode(AcceptedEnum.OK.code)
				.setMessage(AcceptedEnum.OK.message)
				.setData(data);
	}

	/**
	 * 错误响应（需要客户端处理），无附加数据
	 */
	public static ResponseResult<?> fail(ApiExceptionEnum em) {
		return fail(em.code, em.message);
	}

	/**
	 * 错误响应（需要客户端处理），带附加数据
	 */
	public static <T> ResponseResult<T> fail(ApiExceptionEnum em, T data) {
		return fail(em.code, em.message, data);
	}

	/**
	 * 错误响应（基础方法）
	 */
	public static <T> ResponseResult<T> fail(int code, String message) {
		return fail(code, message, null);
	}

	/**
	 * 错误响应（增强版，支持附加数据）
	 */
	public static <T> ResponseResult<T> fail(int code, String message, T data) {
		return new ResponseResult<T>()
				.setCode(code)
				.setMessage(message)
				.setData(data);
	}

	/**
	 * 错误响应（HTTP状态码版本）
	 */
	public static ResponseResult<?> fail(HttpStatus status) {
		return fail(status.value(), status.getReasonPhrase());
	}

	/**
	 * 错误响应（HTTP状态码+附加数据）
	 *
	 * 新增函数：支持通过HTTP状态和自定义数据构造错误响应
	 */
	public static <T> ResponseResult<T> fail(HttpStatus status, T data) {
		return fail(status.value(), status.getReasonPhrase(), data);
	}

	/**
	 * 异常反馈（服务端错误）
	 */
	public static ResponseResult<?> error() {
		return new ResponseResult<>()
				.setCode(AcceptedEnum.ERROR.code)
				.setMessage(AcceptedEnum.ERROR.message);
	}

	/**
	 * 检查响应是否成功
	 */
	public boolean isSuccess() {
		return code == AcceptedEnum.OK.code;
	}

	/**
	 * 检查响应是否失败
	 */
	public boolean isFailed() {
		return !isSuccess();
	}

	// 修改返回类型为当前泛型类型
	public ResponseResult<T> setCode(int code) {
		this.code = code;
		return this;
	}

	// 修改返回类型为当前泛型类型
	public ResponseResult<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	// 修改返回类型为当前泛型类型
	public ResponseResult<T> setData(T data) {
		this.data = data;
		return this;
	}
}