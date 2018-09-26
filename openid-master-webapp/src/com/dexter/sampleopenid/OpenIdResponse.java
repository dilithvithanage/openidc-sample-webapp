package com.dexter.sampleopenid;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import com.miniorange.openid.client.AuthorizationServerRequest;
/**
 * Servlet implementation class OpenIdResponse
 */
public class OpenIdResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthorizationServerRequest authorizationServerRequest;
	private String token, user_info, access_token, id_token;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OpenIdResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		//out.print("code is : "+ code + " State is : "+ state);
		System.out.print("code is : "+ code + " State is : "+ state);
		System.out.print("Making token request : \n");
	
		String hostName = Constants.HOST_NAME;
		String clientSecret = Constants.CLIENT_SECRET;

		String idt = request.getParameter("id_token");
		System.out.println(idt);
		String bodyX = testDecodeJWT(idt);
		//out.print(bodyX);

		try {
			JSONObject js = new JSONObject(bodyX);
			String fname = js.getString("firstname");
			String lname = js.getString("lastname");
			out.print("<h1>");
			out.print("Welcome "+fname+" "+lname+"!");
			out.print("</h1>");
			out.print("\n");
			out.print("\n");
			String mail = js.getString("sub");
			out.print("Email :");
			out.print(mail);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	public String testDecodeJWT(String idt){
		String jwtToken = idt;
		System.out.println("------------ Decode JWT ------------");
		String[] split_string = jwtToken.split("\\.");
		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		String base64EncodedSignature = split_string[2];

		System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(base64EncodedHeader));
		System.out.println("JWT Header : " + header);

		System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
		String body = new String(base64Url.decode(base64EncodedBody));
		System.out.println("JWT Body : "+body);

		return body;

	}

}
