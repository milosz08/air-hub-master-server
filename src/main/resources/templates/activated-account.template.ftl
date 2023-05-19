<#import "common.macro.ftl" as m>

<#assign successfullyActivated = i18n("airhubmaster.email.fragment.successfullyActivated")>

<@m.commonWrapper>
  <div class="space-y-3">
    <p class="lh-sm" style="line-height: 1.25; font-size: 16px; width: 100%; margin: 0;" align="left">
      ${successfullyActivated}
    </p>
  </div>
</@m.commonWrapper>
