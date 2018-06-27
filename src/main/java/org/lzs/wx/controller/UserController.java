package org.lzs.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/")
public class UserController {

    @Value("${wechat.mp.redirect_url}")
    private String redirectUrl;

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMaService wxMaService;

    @RequestMapping("/login")
    public String doLogin(@RequestParam(value = "code", required = false) String code, HttpServletResponse response) throws Exception {
        if (code == null) {
            String authorizationUrl = this.wxMpService.oauth2buildAuthorizationUrl(this.redirectUrl, "snsapi_userinfo", "STATE");
            response.sendRedirect(authorizationUrl);
        } else {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = this.wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = this.wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, wxMpOAuth2AccessToken.getOpenId());
            System.out.println(wxMpUser.getUnionId());

        }
        return "1";


    }

    @RequestMapping("/do_ma_login")
    public WxMaJscode2SessionResult doWxappLogin(@RequestBody HashMap<String, String> params) {
        try {
            WxMaJscode2SessionResult sessionInfo = this.wxMaService.getUserService().getSessionInfo(params.get("code"));
            return  sessionInfo;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return  null;

    }

}
