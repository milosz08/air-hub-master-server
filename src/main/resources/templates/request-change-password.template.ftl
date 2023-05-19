<#import "common.macro.ftl" as m>

<#assign requestChangePasswordTop = i18n("airhubmaster.email.fragment.requestChangePasswordTop")>
<#assign requestChangePasswordBottom = i18n("airhubmaster.email.fragment.requestChangePasswordBottom", [ expirationMinutes ])>

<@m.commonWrapper>
  <div class="space-y-3">
    <p class="lh-sm" style="line-height: 1.25; font-size: 16px; width: 100%; margin: 0;" align="left">
        ${requestChangePasswordTop}
    </p>
    <table class="s-5 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
      <tbody><tr><td style="line-height: 20px; font-size: 20px; width: 100%; height: 20px; margin: 0;" align="left" width="100%" height="20">&#160;</td></tr></tbody>
    </table>
    <table class="alert alert-primary text-center" role="presentation" border="0" cellpadding="0" cellspacing="0" style="border-collapse: separate !important; width: 100%; text-align: center !important; border-width: 0;">
      <tbody>
      <tr><td style="line-height: 24px; font-size: 16px; border-radius: 4px; color: #012e70; margin: 0; padding: 12px 20px; border: 1px solid transparent;" align="center" bgcolor="#d7e7ff">
          <div>
            <strong class="text-2xl" style="font-size: 24px; line-height: 28.8px;"><code>${token}</code></strong>
          </div>
        </td></tr>
      </tbody>
    </table>
    <table class="s-5 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
      <tbody><tr><td style="line-height: 20px; font-size: 20px; width: 100%; height: 20px; margin: 0;" align="left" width="100%" height="20">&#160;</td></tr></tbody>
    </table>
    <p class="lh-sm" style="line-height: 1.25; font-size: 16px; width: 100%; margin: 0;" align="left">
        ${requestChangePasswordBottom}
    </p>
  </div>
</@m.commonWrapper>
