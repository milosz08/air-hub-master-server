<#import "common.macro.ftl" as m>

<#assign successfullyDeletedAccount = i18n("ahms.email.fragment.successfullyDeletedAccount")>

<@m.commonWrapper>
  <div class="space-y-3">
    <p class="lh-sm" style="line-height: 1.25; font-size: 16px; width: 100%; margin: 0;" align="left">
      ${successfullyDeletedAccount}
    </p>
  </div>
</@m.commonWrapper>
