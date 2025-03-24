<#import "common.macro.ftl" as m>

<#assign successfullyChangedPassword = i18n("ahms.email.fragment.successfullyChangedPassword")>

<@m.commonWrapper>
  <div class="space-y-3">
    <p class="lh-sm" style="line-height: 1.25; font-size: 16px; width: 100%; margin: 0;" align="left">
      ${successfullyChangedPassword}
    </p>
  </div>
</@m.commonWrapper>
