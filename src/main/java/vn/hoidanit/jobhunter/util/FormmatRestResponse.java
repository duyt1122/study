package vn.hoidanit.jobhunter.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.response.RestResponse;

@RestControllerAdvice
public class FormmatRestResponse implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return true; // khi nào muốn fornat lại phản hồi true là tất cả trả ra false sẽ không format
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
		int status = servletResponse.getStatus();
		RestResponse<Object> res = new RestResponse<Object>();
		res.setStatusCode(status);
		// return body; // body là data mà ta phản hồi
		// ở thời điểm beforeBodyWrite chạy status code có thể chưa được sét spring cho
		// phép status code được set muộn hơn
		if (body instanceof String) {
			return body;
		}

		if (status >= 400) {
			return body;
		} else {
			res.setData(body);
		}
		return res;

	}

}
