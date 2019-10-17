package cn.tedu.store.service.ex;

/**
 * �������Ʒ������������
 */
public class GoodsNumLimitException extends ServiceException {

	private static final long serialVersionUID = 1587060055636257967L;

	public GoodsNumLimitException() {
		super();
	}

	public GoodsNumLimitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GoodsNumLimitException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoodsNumLimitException(String message) {
		super(message);
	}

	public GoodsNumLimitException(Throwable cause) {
		super(cause);
	}

}
