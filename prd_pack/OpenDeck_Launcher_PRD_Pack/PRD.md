# OpenDeck Launcher — Product Requirements Document

**Document ID:** ODL-PRD-001  
**Version:** 1.1.0  
**Date:** 2026-11-02  
**Status:** Audited implementation baseline — expanded evidence-driven scope  
**Working product name:** OpenDeck Launcher (provisional; rename after repository/package-name check)  
**Target platform:** Android phones and tablets  
**Project type:** Independent, open-source Android home-screen launcher  
**Reference artifact:** Total Launcher 4.1.5, package `com.ss.launcher2`  
**Proposed application ID:** `org.opendeck.launcher` (provisional; must be changed before first public release if unavailable or unsuitable)

---

## 0. Executive decision

Build a new Android launcher that independently implements the broad capability set suggested by the inspected Total Launcher 4.1.5 package and the requested feature-parity goal. The app must not depend on Total Launcher, use its compiled code, copy its graphics/fonts/layout templates, impersonate its brand, patch its licence checks, or reproduce private implementation details. Its data model, rendering engine, editor, integrations, visual design, and source code must be original.

**Commercial/premium policy:** There will be no in-app purchases, subscription, licence server, premium tier, paywall, feature flag that hides paid functionality, or billing SDK. Capabilities that might be premium in the reference product are included as ordinary OpenDeck features. This is an implementation policy, not a claim that the static APK report identified every premium-gated feature.

**Scope interpretation:** The earlier “core” selection is superseded by the latest request for the complete capability set, including premium-equivalent capabilities. Deliver a complete launcher, not merely an app-grid demo. The first release may stage the work into milestones, but the final product scope is the full set below.

**Important evidence limitation:** The supplied bundle has been integrity-checked and its existing static-analysis outputs were reviewed. The available report did not decompile Java/Kotlin methods or observe the reference app at runtime. Therefore, this PRD uses three labels:

- **Observed:** directly present in the static package inventory (manifest entry, class descriptor, asset, or declared intent).
- **Inferred:** a reasonable interpretation of those artefacts, but not proven by execution or method-level analysis.
- **Product requirement:** a capability deliberately specified for OpenDeck, whether or not the reference APK proves it.

Exact one-to-one behaviour and an exhaustive original premium-feature list cannot be certified from this evidence alone. A second static-resource audit expanded the candidate scope to include media controls, photo slideshows, contacts lists, RSS/news panels, a notification panel, toolbox, dynamic images, system gauges, status tiles, grouping, and share-in workflows. Some of these are indicated only by resource names, not by proven execution paths. See `REVERSE_ENGINEERING_EVIDENCE.md` and `GAP_AUDIT.md` for the evidence ledger, confidence labels, and remaining validation requirements.

---

## 1. Product vision

OpenDeck is a local-first, deeply configurable Android launcher. Users can construct their own home-screen layouts from app icons, folders, widgets, text, images, clocks, dates, weather, calendar/contact information, custom actions, and reusable panels/windows. Every placed item is editable, movable, resizable, styleable, layerable, and assignable to supported actions. Users can maintain multiple pages and panels, build gesture/action sequences, apply icon packs and themes, and back up or restore their complete layout.

The product should feel like a small, reliable desktop/workspace builder rather than a fixed icon grid. It must remain usable as a normal launcher even when optional permissions, network access, or integrations are unavailable.

### 1.1 Primary user outcomes

1. Choose OpenDeck as the Android Home app and reach a stable launcher after reboot.
2. Find and launch every visible installed app that Android makes available to the launcher.
3. Create multiple home pages and arrange items freely or with an optional grid.
4. Add, edit, move, resize, duplicate, group, hide, and delete supported items.
5. Add Android widgets using Android's official widget-host flow.
6. Create folders, shortcuts, panels/windows, custom visual elements, and actions.
7. Configure gestures and reusable multi-action sequences within the launcher.
8. Customize wallpaper, background, icons, typography, colours, shadows, opacity, spacing, and transitions.
9. Apply compatible icon packs and independently packaged OpenDeck themes.
10. Export, restore, and share a portable layout backup without copying another app's private data.
11. Use optional weather/news/calendar/contact/notification integrations with transparent, user-controlled permissions.
12. Use all OpenDeck features without an artificial premium tier.
13. Use optional media controls, a configurable notification panel, RSS/news reader, contact grid/list, photo slideshow, animated images, device-status gauges, and a quick-action toolbox when the user enables them.

### 1.2 Non-goals

- Rebuilding or modifying Total Launcher itself.
- Copying Total Launcher's source, DEX, resources, icons, fonts, theme packs, serialized layout assets, screenshots, or proprietary UI text.
- Circumventing Total Launcher billing, licensing, signature verification, or entitlement logic.
- Root access, system-image modification, custom ROM integration, or privileged APIs.
- Replacing Android System UI, notification shade, lock screen, recents screen, or system navigation gestures.
- Running arbitrary shell commands or user-supplied executable scripts.
- Collecting analytics, advertising identifiers, contacts, location, or notifications for monetization.
- Requiring a cloud account or server for basic launcher operation.
- Promising access to hidden apps, managed profiles, or OEM-protected data that Android does not expose.

---

## 2. Reference package and static analysis summary

### 2.1 Inspected artifact identity

| Field | Value |
|---|---|
| Artifact | `com.ss.launcher2_4.1.5-40105_4arch_6dpi_24lang_f666230673d2e4505b5dc11c652d1c98_apkmirror.com.apkm` |
| Reference app | Total Launcher 4.1.5 |
| Package | `com.ss.launcher2` |
| Version / versionCode | `4.1.5` / `40105` |
| APKM outer container | ZIP/JAR-style archive |
| APK members | 35 (base plus configuration splits) |
| Outer archive members | 41 |
| Verified SHA-256 | `b8ae5cba270019e9949783072e173d5145d0ae71714d86c04951281c56c42786` |
| Outer ZIP integrity | PASS (`testzip()` returned no corrupt member) |
| Reported minimum API | 32 |
| Reported compile/target API | 37 / 37 |
| Architectures in bundle metadata | arm64-v8a, armeabi-v7a, x86, x86_64 |
| Main Home activity | `com.ss.launcher2.MainActivity` |

The APKM is a split delivery bundle, not one standalone universal APK. The 35 APK payloads should be treated as one reference release when interpreting the package. The source APK was not installed or executed as part of this inspection.

### 2.2 Directly observed implementation indicators

The available inventory records 41 component declarations across the base and configuration split manifests. The base manifest inventory identifies a Home activity and dedicated activities/classes including:

- `MainActivity`, `RootLayout`, `MainMenuRootLayout`, `MainMenuViewPager`, `MyViewPager`.
- `WindowActivity`, `WindowLayer`, `WindowThumbnailView`, `PickWindowActivity`, `PickWindowByNameActivity`.
- `PickAddableActivity`, `AddableThumbnailView`, `MyPickWidgetActivity`, `MyPickIconActivity`, `PickApplicationActivity`.
- `PickPageActivity`, `PickImageActivity`, `PickTypefaceActivity`, `PickSoundActivity`.
- `EditSequenceActivity`, `PickSequenceActivity`.
- `EditAppFolderActivity`, `PickAppFolderActivity`.
- `BackupManagementActivity`.
- `ApplyThemeActivity`, `SetWallpaperActivity`, `CropImageActivity`.
- `PickTaskerCommandActivity`, `EditSendTaskerVarActivity`, and a Tasker/Locale receiver.
- `NotiListener`, `NotiCountView`, `ContactPhotoView`.
- `BehindEffectLayer`, `PinBoard`, and `ConfirmPinActivity`.

The resource inventory adds further feature indicators (resource-name evidence, not runtime proof):

- Layouts: `layout_media_controller`, `layout_photo_slide`, `layout_rss_reader`, `layout_contacts`, `l_lk_noti_panel`, `layout_noti_panel_options`, and `layout_toolbox`.
- Reusable item layouts: `item_contact_grid`, `item_contact_list`, `item_rss`, `item_rss_wide`, `item_notification`, `item_group`, `item_action`, `item_file`, `item_font`, `item_image`, and `item_window`.
- Editor/resource pickers: `option_pick_dynamic_image_activity_select_mode.xml`, `option_pick_shape_shadow_activity_select_mode.xml`, `layout_resizer`, `layout_resizer_for_wnd`, and `layout_rotator`.
- Drawable catalogue names include battery/CPU/RAM gauge backgrounds, media playback controls, notification badges, Wi-Fi/Bluetooth/DND/ringer/hotspot/signal/brightness states, and image/shape/gesture/action controls. These names are evidence that related visual resources were packaged; they do not prove the app exposes every icon as a working widget or that a status icon can change system state.
- `customization/assets/init.zip` contains a page list, multiple page seed files, a `pinBoard` entry, shape presets (`oval`, `round_rect`, `round_rect_1`), and preferences. The 14,769-byte `elements` asset contains serialized element data. OpenDeck must not copy or depend on that private data format.

These names directly establish that the package contains code components with those names. They do not, by themselves, prove the complete behaviour, screen design, or settings supported by each component.

The base manifest inventory also records 31 activities, 4 services, 4 receivers, and 2 providers. Nineteen entries were listed as explicitly exported in the prior inventory. `MainActivity` has intent actions for `MAIN`, `SEND`, and `SEND_MULTIPLE`; other activities expose standard wallpaper/shortcut actions and Total Launcher-specific theme actions. Exported status is not itself a vulnerability; any equivalent OpenDeck integration endpoint must be deliberately designed and permission-protected. The share filters motivate a safe OpenDeck share-in flow, not a claim that every MIME type is supported by the reference.

### 2.3 Observed permissions and integrations

The static report records 28 unique permission identifiers across the package. Notable groups include:

- App discovery: `QUERY_ALL_PACKAGES`.
- User content: contacts, calendar, and account-related permissions.
- Device/network: location, Wi-Fi/network state, Bluetooth, and Internet.
- Launcher/system UX: wallpaper, wallpaper hints, notification policy, status-bar expansion, and write-settings capability.
- Notifications: a notification-listener service protected by `BIND_NOTIFICATION_LISTENER_SERVICE`.
- Accessibility: a declared accessibility service protected by `BIND_ACCESSIBILITY_SERVICE`.
- Device administration: a device-admin receiver.
- Billing: `com.android.vending.BILLING` and billing proxy activities.
- Biometric: biometric/fingerprint declarations.

OpenDeck must not copy this broad permission set. Each permission is a design decision and must be justified by a feature, requested at the point of use where possible, and documented in the permission ledger in section 7.

### 2.4 Observed external-string indicators

The existing DEX-string inventory contains URL-like strings for an OpenWeatherMap endpoint, geocoding, RSS feeds (including BBC, CBC, El Diario, Le Monde, Spiegel, and YNA), a Google Play icon-pack search, Tasker documentation, and Total Launcher documentation pages. A string in DEX does not prove that the application makes a request to that address. OpenDeck must use its own documented providers and endpoints, prefer HTTPS, and make network-backed modules optional.

### 2.5 Billing indicator and premium boundary

`PurchaseActivity`, Google Play Billing proxy components, the billing permission, and many generic Play Billing library strings are present. This establishes billing integration, not the product's exact paid-feature matrix. The report does not identify SKU names, product entitlements, individual premium feature gates, or a complete list of features that become available after purchase.

**OpenDeck decision:** omit billing entirely. Implement requested capabilities without a premium/purchase branch. Do not derive an entitlement map by guessing from strings.

---

## 3. Feature catalogue and functional requirements

Priority meanings: **P0** = essential for a usable launcher; **P1** = complete product capability; **P2** = optional integration or enhancement. The P-level is OpenDeck delivery priority, not an assertion about Total Launcher's commercial tier.

### 3.1 Launcher lifecycle and Home role — P0

**FR-HOME-001** The application must declare a standard Android Home activity matching `ACTION_MAIN` + `CATEGORY_HOME` + `CATEGORY_DEFAULT`.

**FR-HOME-002** The application must also declare a normal launcher entry (`ACTION_MAIN` + `CATEGORY_LAUNCHER`) so the user can open setup/settings before selecting it as Home.

**FR-HOME-003** First launch must explain how to select OpenDeck as the default Home application. Do not silently change the default launcher.

**FR-HOME-004** The Home surface must load the last saved workspace without requiring network access.

**FR-HOME-005** If a database migration or workspace load fails, show a safe recovery screen with restore/export options; never erase the previous workspace automatically.

**FR-HOME-006** Handle orientation changes, display-size changes, multi-window callbacks where supported, process recreation, and system theme changes without losing workspace state.

**FR-HOME-007** Handle package added, removed, changed, and replaced broadcasts so the app catalogue and icons refresh. Do not continuously poll the installed-app list.

### 3.2 App catalogue, drawer, and search — P0

**FR-APP-001** Query launchable activities via Android package APIs and display the label and application icon.

**FR-APP-002** Provide an app drawer with configurable list/grid layout, column count, icon size, label visibility, sorting, and scrolling direction.

**FR-APP-003** Support alphabetical sorting and a search field that filters visible app labels as the user types.

**FR-APP-004** Support app launch and show a recoverable message when an app is disabled, removed, suspended, or unavailable.

**FR-APP-005** Support long-press app actions where Android exposes them: app info, uninstall request (system-confirmed), pin shortcut, add to workspace, and add to folder.

**FR-APP-006** Support hiding apps from the OpenDeck drawer without uninstalling, disabling, or concealing them from Android settings.

**FR-APP-007** Provide optional app categories/favourites and user-controlled manual ordering.

**FR-APP-008** Use stable component/package identifiers as app references; do not use display names as unique keys.

**FR-APP-009** Refresh icons when packages update or icon-pack selection changes.

**FR-APP-010** Do not claim that every work-profile/private-space app is visible. Display a clear explanation when the operating system restricts visibility.

**FR-APP-011** Accept explicitly supported `ACTION_SEND` and `ACTION_SEND_MULTIPLE` shares (images, plain text/URLs, and OpenDeck backup/theme files) through a preview/import flow. Validate MIME type, URI grants, file size, and content before importing; never auto-place or execute shared content without user confirmation.

### 3.3 Workspace, pages, and freeform layout — P0/P1

The workspace is the core product and must support both a conventional grid and a freeform canvas.

**FR-WS-001** Create, rename, reorder, duplicate, select, and delete multiple home pages.

**FR-WS-002** Support a configurable default page and optional looped page navigation.

**FR-WS-003** Each page has independent background, grid, padding, scroll behaviour, item layout, and optional page-specific transition settings.

**FR-WS-004** Support a grid mode with configurable row/column count, cell spacing, page padding, and snap-to-grid.

**FR-WS-005** Support free positioning using normalized page-relative coordinates, so layout survives resolution and density changes.

**FR-WS-006** Items can be moved, resized, duplicated, rotated where the item type supports it, aligned, distributed, and snapped to optional guides.

**FR-WS-007** Support intentional overlap and independent z-order. Provide send-to-front, send-to-back, raise, lower, and lock-position commands.

**FR-WS-008** Provide an edit mode with selection outlines, resize handles, alignment guides, context actions, and multi-select where practical.

**FR-WS-009** Support undo/redo for workspace editing operations. At minimum, retain the last 30 commands or a configurable memory-safe history.

**FR-WS-010** Provide a page overview/thumbnail navigator for selecting, reordering, and managing pages.

**FR-WS-011** Provide a safe-item deletion flow with undo. Deleting a workspace reference to an Android widget must invoke the correct widget cleanup path only after confirmation.

**FR-WS-012** Support page locking to prevent accidental movement while retaining normal tap actions.

**FR-WS-013** Store item geometry independent of physical pixels. Use density-independent units and normalized coordinates; recalculate at render time.

**FR-WS-014** Handle display cutouts, edge-to-edge insets, status/navigation bars, gesture-navigation insets, and variable aspect ratios.

**FR-WS-015** Support explicit group/ungroup operations for selected workspace items. A group must preserve child geometry, z-order, actions, and accessibility; moving/resizing a group must be reversible.

**FR-WS-016** Support optional page/item depth effects (for example, foreground/background layers or parallax-like visual treatment) only when implemented with public APIs and within the renderer's performance budget. Effects must be independently disableable and respect reduced-motion settings.

### 3.4 Items and custom visual elements — P0/P1

**FR-ITEM-001** Provide an Add panel listing available item types, searchable and grouped by category.

**FR-ITEM-002** A placed item must expose a common editor: position, size, visibility, opacity, rotation (when applicable), z-order, click action, long-press action, and accessibility label.

**FR-ITEM-003** Support app icons, application shortcuts, folders, Android widgets, text labels, images, shapes, spacers, and custom action tiles.

**FR-ITEM-004** Support reusable visual components/templates created by users inside OpenDeck. A template must be stored in OpenDeck's own versioned JSON schema.

**FR-ITEM-005** Support text styling: font family selection from system/user-selected font files, size, weight, colour, alignment, line spacing, letter spacing, shadow, and background.

**FR-ITEM-006** Support image selection through Android's document/photo picker, cropping, scale type, corner radius, opacity, and basic transforms.

**FR-ITEM-007** Support shape primitives: rectangle, rounded rectangle, circle, line, and separator with fill, stroke, gradient where available, and corner radius.

**FR-ITEM-008** Support item-level backgrounds, borders, shadows, gradients, transparency, and padding without forcing the whole page to use the same style.

**FR-ITEM-009** Support reusable item presets and copy/paste of selected items between pages.

**FR-ITEM-010** All custom elements must remain usable with TalkBack where their interaction is meaningful.

**FR-ITEM-011** Support animated image items (including GIF where decoding support is available) with frame-size limits, decoded-memory limits, pause-when-offscreen behaviour, and a static preview fallback. Do not copy the reference app's native libraries or assets.

**FR-ITEM-012** Support a photo-frame/slideshow item backed by user-selected image URIs or user-selected media. Provide interval, order, fit/crop, pause/resume, and missing-source handling. Do not silently enumerate the user's photo library.

**FR-ITEM-013** Support a reusable toolbox/quick-action panel that can contain user-selected OpenDeck actions, app shortcuts, or navigation targets; it must not execute arbitrary shell commands or hidden system APIs.

### 3.5 Folders and app groups — P0/P1

**FR-FOLDER-001** Create folders from apps or shortcuts by drag-and-drop and from the editor.

**FR-FOLDER-002** Rename folders and choose their icon, background, shape, label, open animation, and preview style.

**FR-FOLDER-003** Support add/remove/reorder items within a folder.

**FR-FOLDER-004** Support folder open presentation as an overlay/popup or a dedicated page-like surface. Persist its layout and visual settings.

**FR-FOLDER-005** Handle removed/uninstalled apps inside folders without crashing; show unavailable entries clearly and allow cleanup.

**FR-FOLDER-006** Folder taps open the folder; item taps launch the app/shortcut. Long-press opens folder editing.

### 3.6 Panels/windows — P1

Static inventory includes `WindowActivity`, `WindowLayer`, `WindowThumbnailView`, and window-selection activities. This supports implementing a first-class panel/window capability; exact original interaction details remain unverified.

**FR-WIN-001** Users can create named panels/windows with their own set of visual items and actions.

**FR-WIN-002** A panel may open as a full-screen surface, overlay sheet, or floating panel within the launcher. The selected mode must be explicit and configurable.

**FR-WIN-003** Panels can be opened by a workspace item, folder action, gesture, or action sequence.

**FR-WIN-004** Provide a window/panel picker with searchable names and preview thumbnails.

**FR-WIN-005** Panel size, position, background, opacity, corner radius, animation, and outside-tap behaviour must be configurable where Android window rules permit.

**FR-WIN-006** Panels must not draw over other apps or create hidden system overlays. They are launcher-owned surfaces and must close when the user exits the launcher unless the user explicitly selects a supported persistent behaviour.

### 3.7 Android widgets — P0/P1

**FR-WIDGET-001** Host Android App Widgets through `AppWidgetHost`, `AppWidgetManager`, and `AppWidgetHostView` rather than attempting to recreate third-party widgets.

**FR-WIDGET-002** Present the system-supported widget picker and provider configuration flow.

**FR-WIDGET-003** Allocate and persist appWidget IDs, provider component, page ID, item ID, size, and widget options.

**FR-WIDGET-004** If widget binding requires user approval, display the Android system binding flow and respect cancellation.

**FR-WIDGET-005** Support widget resizing using the provider's declared resize rules and app-widget options.

**FR-WIDGET-006** Restore widget references safely. A backup must not pretend a widget's provider-private data is included. After a restore, prompt the user to rebind unavailable widgets.

**FR-WIDGET-007** Call the widget host lifecycle methods appropriately when the launcher starts, stops, or recreates its workspace.

**FR-WIDGET-008** Handle provider removal, package update, widget configuration failure, and widgets that render slowly or throw exceptions.

**FR-WIDGET-009** Keep widget interactions delegated to the provider's RemoteViews/AppWidgetHostView implementation.

### 3.8 Icons, icon packs, and themes — P1

**FR-THEME-001** Support compatible third-party icon packs through documented Android intent/provider conventions where available.

**FR-THEME-002** Resolve icon-pack activities/resources using package APIs. Provide a fallback to the original app icon if a themed icon is missing.

**FR-THEME-003** Apply icon pack globally or override an individual app icon with a user-selected image.

**FR-THEME-004** Support icon size, label visibility, label font/colour, icon background/mask, and icon shadow controls.

**FR-THEME-005** Support OpenDeck theme packages defined by a public, versioned, documented format. Themes may include original palettes, backgrounds, icon mappings, typography settings, and item templates.

**FR-THEME-006** Provide theme preview, apply, revert, and reset-to-default.

**FR-THEME-007** Never import executable code or arbitrary scripts from a theme package. Treat all theme metadata and media as untrusted files.

**FR-THEME-008** Use only original or properly licensed bundled graphics and fonts. Provide third-party licence notices and provenance.

### 3.9 Wallpaper and backgrounds — P1

**FR-WALL-001** Support setting a device wallpaper through Android's supported wallpaper APIs/system UI.

**FR-WALL-002** Support selecting an image from the system photo/document picker and using it as a page background without forcing it to become the system wallpaper.

**FR-WALL-003** Support crop, fit, fill, center, parallax where safely supported, blur where device resources allow, dim overlay, and per-page background selection.

**FR-WALL-004** Preserve the original selected image URI or copy the asset into app-owned storage after appropriate user selection/consent; avoid broad storage access.

### 3.10 Gestures, click actions, and action sequences — P1

**FR-ACT-001** Provide a configurable gesture map for actions occurring inside the OpenDeck surface: single tap, double tap, long press, swipe up/down/left/right, and optional two-finger gestures.

**FR-ACT-002** Gesture handling must avoid intercepting normal widget touch events unless the user explicitly configures a gesture region or long-press delay.

**FR-ACT-003** Provide a built-in action catalogue: launch app, open app drawer, open search, navigate to page, open panel/window, open folder, open Android settings screen, open app info, change wallpaper, and execute a saved OpenDeck sequence.

**FR-ACT-004** Add only platform-supported actions. For restricted system toggles (Wi-Fi/mobile data/Bluetooth/notification policy/status-bar controls), open the relevant Android settings panel rather than claiming to toggle restricted state directly.

**FR-ACT-005** Support a sequence editor for ordered actions, optional confirmation, delay, stop-on-error/continue-on-error policy, and a visible execution state.

**FR-ACT-006** A sequence must be bounded: maximum 20 steps by default, maximum 5 minutes total, and no background execution after the user leaves the launcher.

**FR-ACT-007** Sequences may use only the built-in allowlisted action types. No shell, arbitrary Intent URI, dynamic code, or hidden API execution.

**FR-ACT-008** Provide an action picker and a readable action summary before saving.

**FR-ACT-009** Sound feedback is optional and disabled by default. Use user-selected/system sounds only.

### 3.11 Tasker/Locale interoperability — P2

Static inventory contains Tasker/Locale-oriented components. OpenDeck should provide a documented integration adapter rather than copying the reference app's broadcast contracts.

**FR-INT-001** Define a stable, versioned OpenDeck integration contract for Tasker/Locale-style automation tools.

**FR-INT-002** External triggers may invoke only an explicitly exported, permission-protected, allowlisted action ID.

**FR-INT-003** Provide a configuration activity for selecting a page, panel, folder, or saved sequence.

**FR-INT-004** Validate all incoming extras, package identity where appropriate, action schema version, and caller permissions. Reject malformed or unsupported requests.

**FR-INT-005** Document the integration contract and include sample automation configuration without requiring a cloud service.

**FR-INT-006** Provide supported Android shortcut-creation flows for user-selected OpenDeck targets such as a named panel/window or folder where the platform allows it. Use the system's confirmation/pinning flow and handle cancellation.

**FR-INT-007** Support the standard application-preferences entry point and wallpaper-setting intent only when the user deliberately invokes them; validate all incoming data and avoid exposing private editor screens to arbitrary callers.

### 3.12 Notification counts, badges, and launcher notification panel — P2

**FR-NOTI-001** Prefer Android's system notification dots where they are sufficient.

**FR-NOTI-002** If numeric badges are implemented, make notification-listener access optional, disabled until the user enables it through Android Settings, and explain exactly what is read and why.

**FR-NOTI-003** Store only the minimum data needed to display per-app counts; do not retain notification content, message bodies, or contact names.

**FR-NOTI-004** Provide per-app badge enable/disable and a global clear/reset option.

**FR-NOTI-005** Do not use an AccessibilityService to scrape notifications or other apps' UI.

**FR-NOTI-006** In addition to numeric badges, provide an optional launcher-owned notification panel that displays currently active notifications only after the user explicitly enables notification-listener access. The panel must be off by default.

**FR-NOTI-007** Notification title/text and action metadata may exist only in volatile memory while rendering the active panel; never persist notification content, export it in backups, send it to a server, or write it to logs. Clear the in-memory snapshot when the listener is disabled or the process/service is torn down.

**FR-NOTI-008** Support user-initiated open-app and dismiss actions where the notification/platform permits. Support inline reply only when the notification itself exposes a valid `RemoteInput` action; require an explicit user action and do not synthesize messages.

**FR-NOTI-009** Group notifications by app and provide clear empty, permission-disabled, and service-disconnected states. Do not attempt to reproduce or replace Android's system notification shade.

### 3.13 Weather, calendar, contacts, and information elements — P1/P2

**FR-INFO-001** Provide optional clock and date elements with configurable format, time zone, locale, font, and alignment.

**FR-INFO-002** Provide optional weather element using a configurable provider. Allow manual city selection as an alternative to device location.

**FR-INFO-003** Request location only when the user enables location-based weather. Manual-city weather must work without location permission.

**FR-INFO-004** Weather must show last-updated time, unavailable/offline state, and units. Cache recent data locally and provide a refresh control.

**FR-INFO-005** Provide optional calendar agenda element through Android Calendar Provider only after a clear permission disclosure and consent.

**FR-INFO-006** Provide optional contact-photo/quick-contact element only after the user selects a contact or grants the minimum required contact permission. Do not enumerate contacts by default.

**FR-INFO-007** Third-party data sources must be configurable and documented. Use HTTPS. Respect provider rate limits and terms.

**FR-INFO-008** RSS/news support is optional and off by default. Users must choose sources; there is no default remote feed polling until they opt in. This is the consent/network policy; the reader behaviour is specified in FR-INFO-010.

**FR-INFO-009** Provide a contacts element in grid and list presentations, with user-selected contact(s) or a clearly disclosed contacts permission. Provide quick-contact actions through supported system intents; use `ACTION_DIAL` rather than placing calls directly.

**FR-INFO-010** Provide an RSS/news reader element with user-added HTTPS feed URLs, refresh controls, per-feed enable/disable, safe XML parsing, bounded item counts, cache expiry, and a visible offline/last-updated state. No bundled feed should be polled by default.

**FR-INFO-011** Provide a photo/contact/resource picker flow for adding selected media and resources to items. Use the Photo Picker or Storage Access Framework and persist URI grants only when offered by the provider.

**FR-INFO-012** Provide clock/date configuration and information elements with optional user-selected fonts and sounds; sound playback must be user-controlled, volume-respectful, and disabled by default.

### 3.14 Media controls, system status, and device gauges — P1/P2

The reference resource inventory contains a media-controller layout and playback-control icons, plus battery/CPU/RAM gauge drawable names and Wi-Fi/Bluetooth/DND/ringer/hotspot/signal/brightness state icons. These are resource-level indicators; their exact reference behaviour is not proven. OpenDeck will provide the following independent features within Android's public API limits.

**FR-MEDIA-001** Provide an optional media-control item that displays the currently active media session's available metadata and supported transport controls (play/pause, next, previous, seek only when exposed by the session).

**FR-MEDIA-002** Access third-party active sessions only through an enabled notification listener or another officially permitted API; never request privileged `MEDIA_CONTENT_CONTROL` access that ordinary apps cannot obtain. Android documents notification-listener access as an allowed route to active sessions. See official API reference in Appendix B.

**FR-MEDIA-003** If notification-listener access is unavailable, show a permission explanation and a safe fallback (launch the selected media app); do not crash or repeatedly prompt.

**FR-MEDIA-004** Keep session metadata ephemeral, do not store playback history, and stop observing sessions when the element is removed or feature is disabled.

**FR-SYS-001** Provide an optional device-status element for information that public APIs expose reliably, including battery percentage/charging state and available-memory information. Values must show unavailable states rather than fabricated numbers.

**FR-SYS-002** CPU-load gauges may be implemented only if a stable, documented, non-privileged API is available on the supported Android versions. Do not read hidden kernel files, use hidden APIs, or request broad device access to manufacture a CPU percentage. Otherwise expose CPU gauge as unsupported on that device.

**FR-SYS-003** Provide status indicators for supported connectivity/device states (for example Wi-Fi, Bluetooth, ringer, DND, hotspot, and signal) only when the required public APIs/permissions expose the state. Do not imply that the launcher can freely toggle restricted system settings.

**FR-SYS-004** Tapping a restricted status tile must open the relevant Android system settings/control surface. Direct state changes are allowed only when a documented API explicitly permits the app and the user has granted the required special access.

**FR-SYS-005** Status and gauge items must refresh efficiently, avoid tight polling, and provide a battery-conscious update interval. They must be optional and disabled when not placed on the workspace.

### 3.15 Backup, restore, import, and export — P0/P1

**FR-BACKUP-001** Provide backup management with create, list, inspect, rename, export, restore, and delete operations.

**FR-BACKUP-002** A backup must include workspace pages, items, folders, panels, visual settings, gestures, sequences, themes' metadata, and user-owned media required by those items.

**FR-BACKUP-003** A backup must not include private app data belonging to other apps, widget provider databases, Android account credentials, notification content, or secrets.

**FR-BACKUP-004** Use a versioned ZIP container containing `manifest.json`, `workspace.json`, and referenced media. Include schema version, OpenDeck version, creation timestamp, file lengths, and SHA-256 digests.

**FR-BACKUP-005** Restore must validate schema, path traversal, file type, file size, item count, checksums, and supported version before applying changes.

**FR-BACKUP-006** Restore must support a preview and explicit Replace, Merge, or Cancel action. Default must be Cancel until user chooses.

**FR-BACKUP-007** Create an automatic safety snapshot before Replace or destructive migration.

**FR-BACKUP-008** Optional password-protected export may use a documented authenticated-encryption format. Do not invent cryptography; use a vetted platform/library implementation.

**FR-BACKUP-009** If a widget provider is absent after restore, preserve its placeholder and show a rebind action rather than failing the whole restore.

### 3.16 Settings, profiles, and portability — P1

**FR-SET-001** Provide searchable settings grouped by Home, Workspace, Drawer, Icons, Gestures, Widgets, Panels, Notifications, Information, Backup, Privacy, and About.

**FR-SET-002** Provide reset per section and full reset with confirmation and a pre-reset snapshot.

**FR-SET-003** Support named local profiles (for example, Work and Personal) as independent workspace configurations.

**FR-SET-004** Switching profiles must not duplicate or delete Android widgets unintentionally; explain that widget IDs may be device-specific.

**FR-SET-005** Provide import/export of profiles using the same validation system as backup.

**FR-SET-006** Reserve translation infrastructure, but ship initial UI in English. Do not mix languages inside a single UI label unless the user chooses a translation.

### 3.17 Accessibility and usability — P0/P1

**FR-A11Y-001** All editor controls must be reachable by keyboard, d-pad, switch access, and TalkBack where applicable.

**FR-A11Y-002** Every workspace item must have a meaningful accessibility label and action description.

**FR-A11Y-003** Provide minimum touch-target sizing and scalable text. Do not rely only on colour to convey selection or errors.

**FR-A11Y-004** Respect system font scaling and display-size settings without corrupting saved geometry.

**FR-A11Y-005** Reduce/disable animations when the system indicates reduced motion or the user disables animations.

---

## 4. UX flows

### 4.1 First launch

1. Welcome screen states that OpenDeck is a Home launcher and explains local storage and optional integrations.
2. Request no sensitive permissions on first launch.
3. Offer `Choose as default Home` button; Android system selection controls the default.
4. Load a clean starter workspace containing only OpenDeck-created demo items (or let user choose Blank / Starter workspace).
5. Show a short editor tutorial: long-press blank space, Add, move/resize, save, and exit edit mode.
6. Present optional feature cards later, at the point of use: widget binding, notification access, location, calendar, contacts.

### 4.2 Add an item

1. Enter edit mode using a clear launcher gesture/menu.
2. Tap Add and choose a category.
3. Choose the item type; show a preview and requirements.
4. Place the item with a reasonable default size.
5. Open its property editor.
6. Save and return to the workspace; provide immediate undo.

### 4.3 Edit a page

1. Long-press blank space or open the page menu.
2. Select page settings or page overview.
3. Change grid/freeform settings, background, transition, or page identity.
4. Preview and Apply/Cancel.
5. Save atomically so a failed edit cannot corrupt the page.

### 4.4 Add an Android widget

1. Select Widgets from Add.
2. Show available providers grouped by app.
3. Allocate widget ID and request binding.
4. If Android requires approval, launch the system binding screen.
5. Start provider configuration where applicable.
6. Place and resize the real widget view.
7. Persist the provider and host metadata; handle cancellation and cleanup.

### 4.5 Restore a backup

1. User selects a backup through Android's document picker.
2. Validate archive and display source version, profile count, page count, item count, and unsupported components.
3. User chooses Replace or Merge.
4. Create safety snapshot.
5. Apply in a database transaction; copy media into app-owned storage only after validation.
6. Rebind Android widgets individually; report missing providers without aborting other data.

---

## 5. Architecture requirements

### 5.1 Technology baseline

- **Language:** Kotlin.
- **Platform:** Native Android SDK.
- **Minimum SDK:** API 26 (Android 8.0) as the proposed baseline. If implementation validation reveals a required API gap, document it before raising minSdk.
- **Compile SDK:** latest stable Android SDK available when the agent starts implementation; the reviewed bundle reports API 37, but do not pin an unavailable preview SDK.
- **Target SDK:** current stable target required by Android distribution policy at release time.
- **UI:** Android Views for the workspace and widget-host surface; native custom `ViewGroup`/`FrameLayout` architecture for low-latency freeform placement. Use XML/Views for settings/editor initially; Compose is not a required dependency.
- **Persistence:** Room/SQLite for workspace entities; DataStore for small preferences.
- **Async work:** Kotlin coroutines with lifecycle-aware scopes; WorkManager only for user-requested deferrable refresh/backup tasks.
- **Image handling:** Android platform APIs and a small maintained library only when justified.
- **Build:** Gradle Kotlin DSL, version catalog, reproducible dependency versions.
- **Tests:** JUnit, AndroidX instrumentation/UI tests, and repository/domain tests.

Avoid large frameworks, embedded web runtimes, local databases from third parties, background daemons, or unnecessary native code. Keep the workspace renderer independent from settings screens.

### 5.2 Suggested Gradle modules

Start as a modular single Android application. Do not create many modules before there are stable boundaries.

```text
:app                  Android application, manifest, navigation, lifecycle
:core:model           Domain entities and value objects
:core:data            Room database, repositories, DataStore, backup persistence
:core:workspace       Workspace engine, page/item layout, edit/drag controllers
:feature:drawer       App catalogue, drawer, search, icon resolution
:feature:editor       Page/item/folder/panel editors
:feature:widgets      AppWidgetHost lifecycle and picker/bind flow
:feature:themes       Icon packs, theme packages, wallpaper/background selection
:feature:actions      Gestures, action catalogue, sequences, Tasker adapter
:feature:integrations Optional weather, RSS, calendar, contacts, notification panel/badges, media session, and device-status adapters
```

If the project is too expensive to build on the initial machine, collapse modules into packages first; preserve dependency direction and interfaces so extraction is straightforward.

### 5.3 Architecture pattern

Use a layered architecture:

- **Presentation:** Activities, Fragments or custom Views, editor dialogs, settings screens, view models.
- **Domain:** workspace use cases, layout calculations, action validation, backup orchestration, permission-gated feature policies.
- **Data:** Room DAOs, repositories, DataStore, media storage, backup codecs, Android platform adapters.
- **Platform adapters:** PackageManager, AppWidgetManager/AppWidgetHost, WallpaperManager, ShortcutManager, notification listener, Calendar Provider, Contacts Provider, SAF.

Do not let UI classes execute SQL or parse backup ZIPs. Do not let repository classes keep Activity references. Do not create a global singleton holding a Context or an entire view hierarchy.

### 5.4 Workspace renderer

Use one workspace host `ViewGroup` and a page container per page. A `WorkspaceItemView` base abstraction should provide common geometry, style, accessibility, and gesture interfaces. Concrete renderers handle app icon, folder, text, image, shape, custom action, and widget-host content.

- Android widgets must be real `AppWidgetHostView` instances managed by a stable host ID.
- Draw only non-interactive decoration into a cached background layer where possible.
- Avoid full-page bitmap snapshots for live workspace content.
- Use view pooling only after profiling; correctness comes first.
- Perform layout calculation in density-independent coordinates and transform to screen bounds at render time.
- Keep drag previews lightweight; do not rebuild the entire view tree for every pointer event.

### 5.5 Persistence model

Minimum domain entities:

| Entity | Key fields |
|---|---|
| `WorkspaceProfile` | id, name, schemaVersion, createdAt, updatedAt, defaultPageId |
| `WorkspacePage` | id, profileId, orderIndex, name, gridRows, gridColumns, pageStyleJson, transitionId |
| `WorkspaceItem` | id, pageId, type, xNorm, yNorm, widthNorm, heightNorm, rotation, zIndex, locked, visible, styleJson, actionId |
| `Folder` | id, itemId, title, presentationStyleJson |
| `FolderEntry` | id, folderId, targetType, packageName, componentName, shortcutId, orderIndex |
| `Panel` | id, profileId, name, presentationMode, sizeJson, styleJson |
| `PanelItem` | item fields scoped to a panel |
| `WidgetBinding` | itemId, appWidgetId, providerPackage, providerClass, optionsJson |
| `GestureBinding` | id, profileId/pageId/region, gestureType, actionSequenceId |
| `ActionSequence` | id, name, schemaVersion, stepsJson, enabled |
| `MediaAsset` | id, relativePath, mimeType, byteLength, sha256, ownerId |
| `ThemeSelection` | profileId, iconPackPackage, themeId, perAppOverridesJson |
| `FeedSource` | id, profileId, url, title, enabled, refreshInterval, lastFetchAt, cacheExpiresAt |
| `NotificationPanelPreference` | profileId, enabled, groupingMode, perPackageRulesJson (no notification payloads) |
| `MediaControlPreference` | profileId, enabled, selectedPackageAllowlist, displayOptionsJson |
| `StatusTilePreference` | profileId, tileType, enabled, displayMode, refreshPolicy |
| `SlideshowPreference` | itemId, sourceUris, intervalMs, orderMode, scaleMode |

Use foreign keys and transactions. Keep external package/component identifiers nullable because apps can be removed. Store styles as versioned, validated JSON or normalized tables; avoid a single unvalidated JSON blob for all critical state.

### 5.6 Coordinate and style schema

- Persist normalized geometry relative to a logical page canvas (0.0–1.0), not physical pixels.
- Keep optional grid coordinates for snap-to-grid items.
- Define a stable style schema with explicit defaults, enums, bounds, and migration rules.
- Use z-index as an ordered integer; avoid float-based ordering.
- Validate all geometry values are finite and within safe ranges before render.
- Preserve unsupported style keys during import only if the schema explicitly allows forward-compatible unknown fields; never interpret unknown fields as executable instructions.

### 5.7 Action engine

An action is a typed data object. It is not a raw Android `Intent` supplied by an untrusted file. Define a sealed Kotlin action hierarchy and a central validator/executor. Require explicit user interaction for launching external settings or communication intents. Use safe intent constructors with known actions and validated package/component names. Never use `Runtime.exec`, `ProcessBuilder`, reflection into hidden Android APIs, or arbitrary deep-link execution from imported files.

### 5.8 Backup format

Suggested format:

```text
.opendesk
  manifest.json
  profile.json
  pages.json
  items.json
  folders.json
  panels.json
  actions.json
  themes.json
  media/
    <sha256>.<extension>
```

The file extension may be `.odbackup`; the physical container may be ZIP. `manifest.json` must declare format version, app version, created timestamp, entry count, lengths, SHA-256 digests, optional encryption descriptor, and supported-feature summary. The restore pipeline must reject absolute paths, `..` traversal, symlinks, oversized files, excessive compression ratios, unknown executable content, and malformed JSON.

---

## 6. Android platform constraints and supported alternatives

A launcher is not a privileged system application. Do not promise capabilities Android does not grant to ordinary apps.

| Desired behaviour | OpenDeck approach / limitation |
|---|---|
| Become the default Home app | Register `CATEGORY_HOME`; user selects it through Android's role/default-app UI. |
| Discover launchable apps | Query `ACTION_MAIN` + `CATEGORY_LAUNCHER`; use targeted `<queries>` declarations first. Broad `QUERY_ALL_PACKAGES` is subject to distribution policy review. |
| Host Android widgets | Use `AppWidgetHost`; Android may show a user approval flow before binding. |
| Pin supported shortcuts | Use `ShortcutManager`/AndroidX compatibility APIs and respect user/system approval. |
| Notification badges/panel and media-session access | Optional `NotificationListenerService`, explicitly enabled by the user in Settings; counts may be cached briefly and notification content may exist only in volatile memory while the panel is open. Never persist or transmit notification text. The enabled listener can also be supplied to `MediaSessionManager` for active-session access. |
| Toggle Wi-Fi/mobile data or restricted system state | Do not claim direct control; open the relevant system settings/control panel. Status indicators must show unavailable when the OS does not expose the state. |
| Capture system navigation gestures | Not supported as a general third-party launcher feature; handle gestures only within OpenDeck's own surface. |
| Change status bar/notification shade globally | Respect Android/OEM restrictions; use allowed window-inset/status-bar appearance APIs only. |
| Read contacts/calendar | Optional feature, clear purpose and just-in-time permission. |
| Location-based weather | Optional runtime location access; manual city entry remains available. |
| Accessibility service | Not required for normal launcher features. Avoid it unless a clearly defined, compliant assistive feature is added with disclosure and consent. |
| Device-admin receiver | Not needed for launcher functionality; do not include in initial release. |
| Work profile/private-space contents | Visibility and launching depend on Android and profile policy; explain unavailable apps rather than bypassing profile controls. |

Relevant official references are listed in `REVERSE_ENGINEERING_EVIDENCE.md` and the citations in the handoff answer.

---

## 7. Permission and privacy requirements

### 7.1 Principles

1. Ask for no sensitive permission during initial onboarding.
2. Request permissions only when the user activates the feature that needs them.
3. Explain the data accessed, purpose, local retention, and how to disable the feature.
4. Prefer system pickers and narrow APIs over broad storage/app access.
5. No advertising SDK, analytics SDK, fingerprinting, or third-party telemetry by default.
6. All core launcher features work offline after installation.
7. No data leaves the device except for an explicitly enabled weather/news/provider feature.
8. Provide a privacy screen showing active optional permissions and links to system settings.

### 7.2 Permission ledger

| Capability | Permission/API | Policy |
|---|---|---|
| App drawer | Launcher-intent package queries | Declare the narrow query requirements. Add `QUERY_ALL_PACKAGES` only if necessary, justified, and approved for the chosen distribution channel. |
| Wallpaper | Wallpaper API and selected image URI | Request only what the official API requires; use system image picker. |
| User image/font selection | Storage Access Framework / Photo Picker | No broad storage permission. |
| Weather by city | Internet | Optional module; manual city does not require location. |
| Weather by current location | Coarse/fine location at point of use | User-selectable alternative; allow manual location. |
| Calendar element | `READ_CALENDAR` when feature enabled | Optional; do not scan on app start. |
| Contact photo element | `READ_CONTACTS` or a user-selected contact URI | Ask only when adding/selecting a contact. |
| Notification badges/panel and media sessions | Notification listener special access | Optional; badges may use counts, panel text is ephemeral only while displayed, and media session metadata is not persisted. Never log or transmit notification content. |
| Biometric confirmation | `BiometricPrompt` | Only for a separately scoped local privacy feature; not required for core launcher. |
| App uninstall request | System uninstall confirmation flow | No silent uninstall. |
| Make phone call | `ACTION_DIAL` | Open dialler; do not request `CALL_PHONE` for the initial product. |
| Change restricted settings | Settings panel/system activity | No direct privileged toggles. |
| Accessibility | None for standard launcher | Do not include a service by default. |
| Device administration | None | Do not include. |
| Wi-Fi/Bluetooth state | No direct toggle in baseline | Open system controls/settings when needed. |

### 7.3 Data retention

- Workspace database and media are stored in app-private storage.
- User-selected document URIs are persisted only when the system grants persistable access; otherwise copy the selected image into app-owned media storage.
- Notification counts may be cached briefly. Notification title/text/action data exists only in volatile memory while the launcher notification panel is active; never persist, back up, log, or transmit it.
- Weather cache has a short configurable TTL and can be cleared.
- Logs must not contain contact names, notification bodies, coordinates, or raw imported backup contents.
- Add a one-tap `Clear optional integration data` action.

---

## 8. Non-functional requirements

### 8.1 Performance

Targets are measurable release-build goals, not guarantees for every OEM device:

- Cold start to interactive saved Home surface: target ≤2.5 seconds on a representative mid-range device, with no network dependency.
- Warm resume to interactive: target ≤1.0 second.
- Workspace drag/resize should remain smooth; target 60 fps on a representative modern phone with a normal-sized page.
- No full database scan or image decode on the main thread.
- Lazy-load page content and media. Keep widget provider work isolated from workspace layout.
- Animated images and slideshows must pause when their page/panel is off-screen; cap concurrent decoders and decoded bitmap memory, and degrade to static previews under memory pressure.
- Media-session and notification listeners must subscribe only while their feature is enabled and release callbacks/listeners promptly when disabled. RSS refresh must be user-configurable and rate-limited.
- Baseline idle memory target: ≤120 MB RSS on a representative 4 GB device, excluding external widget-provider memory; investigate any sustained growth.
- Startup must not synchronously query weather, RSS, contacts, calendar, or notification history.
- APK size target: ≤35 MB for arm64 universal release before optional downloadable theme packs; report actual size per build.

### 8.2 Reliability

- No data loss on force-stop, process death, reboot, or orientation change.
- Database migrations must be tested from every released schema version.
- A corrupt optional media file must not prevent the launcher from opening.
- A bad theme/backup must be rejected without altering the active workspace.
- An unavailable app/widget provider must produce a repairable placeholder.
- All critical writes must be transactional and support recovery.

### 8.3 Compatibility

- Minimum Android API 26 unless a validated platform constraint requires raising it.
- Test current Android releases at implementation/release time, including API 37 if stable and available.
- Test at least one AOSP/Pixel-like device and multiple OEM launcher environments (Motorola, Samsung, Xiaomi/Redmi where available).
- Support portrait and landscape; adapt workspace dimensions to tablets/foldables without assuming one fixed aspect ratio.

### 8.4 Accessibility

- Support TalkBack labels, keyboard focus, large fonts, contrast, reduced motion, and touch targets.
- Provide an editor mode that does not depend exclusively on drag gestures; offer explicit Move/Resize buttons and numeric geometry controls.

### 8.5 Maintainability

- No production Kotlin file should exceed 500 lines without a documented reason.
- Use `ktlint` or equivalent formatting, Android Lint, dependency update review, and code owners/PR review.
- Keep platform adapters small and testable.
- All exported components must have documented security requirements.
- Keep an architecture decision record for decisions that affect backup format, widget lifecycle, action execution, and permissions.

---

## 9. Release scope and milestones

The complete product scope is the whole document. Milestones sequence implementation; they do not remove features from the final scope.

### M0 — Repository and build foundation

- Create Android project, package identity, licence, README, Code of Conduct, CONTRIBUTING, SECURITY, dependency policy.
- CI build, lint, unit-test workflow.
- Home intent filters and default-launcher onboarding.
- Room schema, DataStore, migrations baseline.

**Exit:** debug APK installs, can be selected as Home, opens a stable blank workspace, survives process recreation.

### M1 — Usable launcher foundation

- App catalogue and drawer.
- Search, sort, favourites, app launch, package refresh, safe share-in/import preview.
- Basic home pages, dock, app icons, folders.
- Basic settings and first-run starter workspace.

**Exit:** user can use OpenDeck as a daily launcher without a network connection.

### M2 — Workspace editor and freeform engine

- Edit mode, freeform coordinates, optional grid/snap.
- Addable items, drag, resize, rotate where supported, z-order, undo/redo.
- Page overview and page settings.
- Text, image, animated image, photo slideshow, shape, grouped elements, toolbox, and action elements.

**Exit:** workspace layout is editable and persists correctly across density/orientation changes.

### M3 — Widgets, panels, templates

- AppWidgetHost, provider picker, binding, configuration, resize, recovery.
- Named panels/windows and previews.
- User-created reusable templates.

**Exit:** third-party Android widgets work through official host APIs; panels can open/close from actions.

### M4 — Customization system

- Icon pack compatibility, per-app icon overrides.
- Theme format, previews, apply/revert.
- Wallpaper/page backgrounds, fonts, image crop, styles/effects.
- Transitions and optional animation controls.

**Exit:** user can create and restore a visually customized workspace without copied proprietary assets.

### M5 — Actions and integrations

- Gesture map, action catalogue, sequence editor.
- Optional Tasker/Locale and Android shortcut integrations.
- Optional numeric badges and launcher notification panel.
- Optional weather, RSS reader, calendar, contact grid/list, media controls, battery/memory gauges, and device-status tiles.

**Exit:** every optional integration is disabled by default, permission-gated, documented, and testable.

### M6 — Backup, profiles, hardening

- Versioned backup/restore/import/export.
- Profiles, merge/replace, validation, safety snapshots.
- Permission review, exported-component audit, privacy screen, accessibility audit.
- Performance and OEM compatibility tests.

**Exit:** backup round-trip passes; no known critical data-loss or privilege-escalation defects remain.

### M7 — Open-source release

- Release signing, checksums, provenance, dependency/SBOM report.
- GitHub release notes, installation instructions, known limitations.
- License and attribution audit.
- APK smoke test on clean devices.

**Exit:** reproducible release pipeline, installable APK, documented release process, and public issue templates.

---

## 10. Acceptance criteria (release gate)

The product is not considered feature-complete or eligible for a full-capability parity claim until every P0, P1, and P2 functional requirement in this PRD is implemented and passes its relevant tests. P2 labels mean optional/user-enabled capabilities, not capabilities that can be silently omitted. An early preview may ship with a published gap list, but it must not be labelled complete.

### Launcher and app catalogue

- [ ] Android offers OpenDeck as a Home app and the user can set/reset the default.
- [ ] Home loads offline after force-stop and reboot.
- [ ] App drawer lists available launchable apps and launches them.
- [ ] App search, sorting, favourites, hidden-app list, and package refresh work.
- [ ] App removal/update does not crash the launcher or corrupt folders.

### Workspace editor

- [ ] Create at least 10 pages, reorder them, and select a default page.
- [ ] Place 100 lightweight elements on a stress-test page without unrecoverable layout failure.
- [ ] Drag, resize, align, change z-order, lock, duplicate, delete, undo, and redo.
- [ ] Change grid and display size; normalized positions remain visually consistent.
- [ ] Save, kill process, reopen; every page/item/property remains intact.

### Items, folders, windows

- [ ] Add/edit text, static and animated images, photo slideshow, shape, icon, folder, group, toolbox, and action tile.
- [ ] Group/ungroup selected items without changing child geometry, actions, or z-order.
- [ ] Animated images pause off-screen; slideshow sources are user-selected and revoked URIs recover safely.
- [ ] Folder entries can be reordered and launch their target apps.
- [ ] Create a panel/toolbox, open it by tap and gesture, close it, then reopen it with its state intact.
- [ ] Unavailable app/shortcut entries show safe placeholders.
- [ ] Share an image and an OpenDeck backup to OpenDeck; preview, cancel, and confirmed import paths work.
- [ ] Create a user-confirmed system shortcut for a supported panel/folder where the Android version permits it.

### Widgets

- [ ] Add widgets from at least three different provider apps.
- [ ] User-approved binding and provider configuration work.
- [ ] Resize and restore widget references without duplicate or leaked appWidget IDs.
- [ ] Missing provider after restore yields a repairable placeholder.

### Customization

- [ ] Apply and revert an icon pack.
- [ ] Override one app icon without changing the rest.
- [ ] Apply and revert a theme package.
- [ ] Change wallpaper and page background independently.
- [ ] Apply font, colour, opacity, shadow, shape, and transition settings.

### Gestures and sequences

- [ ] Each supported gesture can be configured and reset.
- [ ] Widget touch events are not unexpectedly swallowed.
- [ ] Sequence validation blocks unknown or unsafe action types.
- [ ] Sequence cancellation and timeout work.

### Information, media, and optional integrations

- [ ] Contacts grid/list uses only user-selected entries or an explicitly granted permission; phone actions open the dialer.
- [ ] RSS reader accepts user-configured HTTPS feeds, safely parses malformed content, enforces item/response limits, and shows offline/last-updated states.
- [ ] Media controller displays and controls only supported active sessions through a documented user-authorized API path.
- [ ] Notification panel is opt-in, shows current notifications, supports only exposed user-triggered actions, and never persists or logs content.
- [ ] Battery/memory indicators show actual public-API values; CPU gauge reports unsupported where no safe API exists.
- [ ] Status tiles open system controls where direct toggles are restricted and do not claim unsupported control.
- [ ] RSS refresh, media listeners, notification listeners, GIF animation, and status polling stop or pause when their feature is disabled/off-screen.

### Backup and restore

- [ ] Export a profile, erase app data, reinstall, and restore the exported profile.
- [ ] Checksum mismatch, path traversal, oversized archive, malformed JSON, and unsupported version are rejected.
- [ ] Replace creates a safety snapshot.
- [ ] Merge does not overwrite existing IDs accidentally.
- [ ] User receives a clear list of missing widget providers and media assets.

### Privacy and security

- [ ] Core launcher works with optional permissions denied and with all network-backed integrations disabled.
- [ ] No contacts/calendar/location/notification data is read before the corresponding feature is enabled.
- [ ] No notification body or contact detail appears in logs.
- [ ] No arbitrary shell execution or dynamic code loading exists.
- [ ] Every exported component is tested against malformed and unauthorized input.
- [ ] No crash or data mutation occurs from a malformed imported theme/backup.

### Build and release

- [ ] `assembleDebug`, `test`, and `lint` pass in CI.
- [ ] Release APK installs and launches on API 26 and a current Android release.
- [ ] Release signing key is not committed to source control.
- [ ] SHA-256 and dependency licence/SBOM report are attached to release.

---

## 11. Test strategy

1. **Unit tests:** geometry normalization, grid snapping, z-order/group operations, folder ordering, action validation, sequence timeout logic, backup manifest and hash validation, theme/RSS parsing, share MIME validation, slideshow rotation policy, and migration logic.
2. **Repository tests:** Room transactions, profile switching, package-catalogue cache invalidation, media reference cleanup, missing provider handling.
3. **Instrumentation tests:** Home role, Activity lifecycle, widget bind/configure/resize, document picker/share-in, wallpaper flow, notification-listener grant/revocation, media-session access, notification actions/RemoteInput, and biometric APIs if added.
4. **UI tests:** first launch, select Home, drawer search, add/edit item, folder/group edit, panel open/close, RSS/contacts/notification/media panels, slideshow controls, theme preview, and backup export/restore.
5. **Compatibility tests:** API 26 baseline, API 29, API 30 package visibility, API 33+ notification permission behaviour, API 35+, latest stable API, OEM-specific Home behaviour.
6. **Performance tests:** cold start, page switch, drag/resize, scroll drawer, icon cache, bitmap/GIF decode, slideshow off-screen pause, memory after repeated page switching, widget stress, and bounded RSS refresh.
7. **Fuzz/negative tests:** malformed JSON, unexpected enum, negative dimensions, `NaN`/infinite coordinates, malicious ZIP paths, huge decompression ratio, corrupt image, invalid package component, repeated external integration calls.
8. **Manual tests:** widget providers, OEM gesture navigation, system font scaling, launcher switching, reboot, work profile and private-space limitations.

Do not use an emulator as the only validation environment. Test on a physical device. For a low-resource development laptop, build in CI and install APKs over USB instead of keeping a heavyweight emulator running.

---

## 12. Repository layout and engineering conventions

Suggested repository:

```text
opendeck-launcher/
├── AGENTS.md
├── README.md
├── LICENSE
├── NOTICE
├── CONTRIBUTING.md
├── CODE_OF_CONDUCT.md
├── SECURITY.md
├── CHANGELOG.md
├── settings.gradle.kts
├── build.gradle.kts
├── gradle/libs.versions.toml
├── gradle.properties
├── app/
├── core/
├── feature/
├── docs/
│   ├── PRD.md
│   ├── ARCHITECTURE.md
│   ├── DATA_MODEL.md
│   ├── BACKUP_FORMAT.md
│   ├── PERMISSIONS.md
│   ├── INTEGRATIONS.md
│   ├── TEST_PLAN.md
│   ├── RELEASE.md
│   └── decisions/
├── fastlane/                 # optional release metadata
└── .github/workflows/
    ├── android-ci.yml
    └── release.yml
```

Engineering rules:

- Kotlin only for app/domain implementation; avoid adding another runtime.
- No Python or Node.js runtime dependency in the Android APK.
- Keep classes focused and files reasonably small (target under 500 lines).
- Use explicit interfaces around PackageManager, AppWidgetManager, SAF, WallpaperManager, and special-access services.
- Every public action type and backup format field must have schema documentation.
- No arbitrary code from theme packs, backup archives, or integrations.
- Do not commit generated signing credentials, local.properties, private keys, or user backups.
- Add unit tests with each domain feature, not after the entire app is built.
- Pin dependency versions and review licences before adding dependencies.

### 12.1 Build settings for a low-resource development laptop

The project must be buildable on a modest Linux laptop. Start with conservative Gradle settings and tune only after measuring:

```properties
# gradle.properties
org.gradle.jvmargs=-Xmx1536m -Dfile.encoding=UTF-8
org.gradle.workers.max=2
org.gradle.parallel=false
org.gradle.caching=true
kotlin.daemon.jvmargs=-Xmx768m
android.useAndroidX=true
```

Use a physical Android phone for manual testing. Avoid running an Android emulator and a full IDE at the same time on a 6 GB RAM machine. A CI runner can perform release builds and instrumentation jobs.

---

## 13. Open-source and legal requirements

- Recommended licence: **Apache License 2.0** for a permissive open-source project with an explicit patent grant. The maintainer may choose MIT instead before implementation, but the licence must be selected before accepting external contributions.
- Use a unique product name, package ID, application icon, UI copy, artwork, themes, and documentation.
- Do not copy Total Launcher source, binary methods, art, assets, serialized sample layouts, fonts, proprietary strings, or screenshots into OpenDeck.
- Do not modify or distribute the reference APK as part of OpenDeck.
- Do not use Total Launcher branding in the app name, launcher label, icon, or store listing. The reference may be mentioned factually in a compatibility/design document with a clear independent-project disclaimer.
- Check the licence of all dependencies, fonts, icons, sample wallpapers, and community themes. Preserve notices.
- Theme import/export format and integration API must be original and public.
- Keep a provenance list for every bundled asset.

This PRD is a product specification, not a legal opinion. The project maintainer must review applicable licences and distribution terms before public release.

---

## 14. Risks and mitigations

| Risk | Impact | Mitigation |
|---|---|---|
| Static inspection cannot reveal all screens/settings/paid gates | Incomplete parity | Maintain an observed/inferred/unknown feature ledger; conduct manual, authorized walkthroughs of the reference app before claiming exact parity. |
| Android/OEM launcher behaviour varies | Default-home or gesture differences | Test multiple OEMs and provide clear fallbacks. |
| Broad package visibility is restricted by distribution policy | Drawer missing apps on some distribution paths | Use launcher-intent queries and justify any broader permission with policy documentation. |
| Widget providers behave inconsistently | Crashes or rendering problems | Isolate widget views, add exception recovery, and test with varied providers. |
| Widget IDs cannot be transferred across devices | Backup appears incomplete | Preserve placeholders and rebind at restore time. |
| Notification access is sensitive | User trust and policy risk | Optional feature, explicit disclosure; notification text is used only in volatile memory while the panel is displayed and is never retained, logged, backed up, or transmitted. |
| Accessibility API restrictions | Distribution rejection or user risk | Do not use it for ordinary gesture automation or UI scraping. |
| Freeform workspace has performance cost | Jank and memory pressure | Custom ViewGroup, lazy loading, normalized geometry, caches, profile before optimizing. |
| Third-party theme package is malicious or malformed | Data corruption or unsafe input | JSON schema validation, media sandboxing, no executable code, size limits. |
| User expects exact Total Launcher premium parity | Feature mismatch | Distinguish verified behaviour from inferred capabilities; validate with a manual reference test matrix. |

---

## 15. Definition of done

OpenDeck is ready for its first stable open-source release when:

1. Every P0, P1, and P2 functional requirement in this PRD is implemented and tested before claiming full feature completeness.
2. An early alpha/beta may defer requirements only with a public gap list, but that build must not be described as the completed launcher or full capability parity.
3. The app is a functioning Android Home launcher, not a mock UI or screenshot prototype.
4. Workspace, pages, folders, panels, actions, themes, and backups use an original, documented data model.
5. Widget handling uses official Android widget-host APIs; notification/media integrations use user-enabled system access only; feeds, share imports, and media assets are validated as untrusted input.
6. Core usage works offline and with optional permissions denied.
7. No Total Launcher source code, assets, fonts, copied layout templates, or brand identity are included.
8. No billing, subscription, licence gate, or paid-feature branch exists in OpenDeck.
9. CI builds and tests pass, and a signed APK is available with a checksum.
10. README, architecture, privacy, permission, backup, integration, and release documents are complete.

---

## Appendix A — Recommended agent execution order

An AI coding agent must not try to implement every feature in one giant patch. Work in small, buildable increments:

1. Read `AGENTS.md`, this PRD, `REVERSE_ENGINEERING_EVIDENCE.md`, and `ARCHITECTURE.md`.
2. Create the Gradle project and CI; build an empty Home app.
3. Implement Room schema and migration tests.
4. Implement app discovery and drawer.
5. Implement workspace page renderer with app icons.
6. Implement edit mode, geometry, grid/freeform, and undo/redo.
7. Implement folders and simple visual items.
8. Implement AppWidgetHost and widget lifecycle.
9. Implement panels/windows.
10. Implement themes/icon packs/wallpapers.
11. Implement gestures and safe action sequences.
12. Implement backup/restore and profiles.
13. Implement optional integrations one by one, each behind a dedicated feature setting.
14. Run the full acceptance suite and fix failures before adding further polish.
15. Produce the signed APK, checksum, and release notes.

At every step: compile, run relevant tests, inspect the diff, and report exactly what is implemented, partial, missing, incorrect, and not yet verified.

## Appendix B — Official Android documentation

- Home and launcher intent categories: https://developer.android.com/reference/android/content/Intent#CATEGORY_HOME and https://developer.android.com/guide/components/intents-filters
- App widget host: https://developer.android.com/develop/ui/views/appwidgets/host
- AppWidgetHost API: https://developer.android.com/reference/android/appwidget/AppWidgetHost
- AppWidgetManager API: https://developer.android.com/reference/android/appwidget/AppWidgetManager
- Package visibility: https://developer.android.com/training/package-visibility
- Shortcut creation/pinning: https://developer.android.com/develop/ui/compose/system/shortcuts/creating-shortcuts
- NotificationListenerService: https://developer.android.com/reference/android/service/notification/NotificationListenerService
- Google Play package-visibility policy: https://support.google.com/googleplay/android-developer/answer/10158779
- Google Play Accessibility API policy: https://support.google.com/googleplay/android-developer/answer/10964491
- Active media sessions and notification-listener access: https://developer.android.com/reference/android/media/session/MediaSessionManager
- Storage Access Framework and user-selected document URI grants: https://developer.android.com/training/data-storage/shared/documents-files
