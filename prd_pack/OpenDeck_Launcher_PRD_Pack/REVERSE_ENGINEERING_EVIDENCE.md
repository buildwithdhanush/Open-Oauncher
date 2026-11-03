# Total Launcher 4.1.5 — Static Evidence Ledger for OpenDeck

**Date:** 2026-11-02  
**Target:** `com.ss.launcher2`, version 4.1.5 (versionCode 40105)  
**Source:** user-supplied APKMirror APKM bundle  
**Analysis mode:** static inventory and prior exported analysis artifacts; no install, execution, runtime tracing, or network capture in this pass.

## 1. Artifact verification

- File: `com.ss.launcher2_4.1.5-40105_4arch_6dpi_24lang_f666230673d2e4505b5dc11c652d1c98_apkmirror.com.apkm`
- SHA-256: `b8ae5cba270019e9949783072e173d5145d0ae71714d86c04951281c56c42786`
- Container: ZIP/JAR-style APKM.
- Outer archive members: 41.
- APK payloads: 35.
- ZIP integrity test: PASS; no corrupt member reported.
- Package metadata: `com.ss.launcher2`, Total Launcher 4.1.5, versionCode 40105.
- Metadata reports min API 32, target/compile API 37, four CPU ABIs and six density groups.

The bundle contains one base APK plus configuration splits. Analysing or installing only the base APK can omit split-specific resources/native libraries.

## 2. Direct observations from supplied static report/inventory

| Evidence ID | Direct observation | Confidence | Does not establish |
|---|---|---|---|
| E-001 | Main activity has `MAIN`, `HOME`, `DEFAULT`, and `LAUNCHER` intent categories. | High | Actual user default-launcher selection or runtime launch success. |
| E-002 | Inventory records `RootLayout`, `MainMenuRootLayout`, `MainMenuViewPager`, and `MyViewPager`. | High | Exact page transition or workspace rendering semantics. |
| E-003 | Inventory records `WindowActivity`, `WindowLayer`, `WindowThumbnailView`, `PickWindowActivity`, and `PickWindowByNameActivity`. | High | Exact meaning of each window mode or exact UI controls. |
| E-004 | Inventory records `PickAddableActivity`, `AddableThumbnailView`, `MyPickWidgetActivity`, `MyPickIconActivity`, and `PickApplicationActivity`. | High | Full supported item catalogue. |
| E-005 | Inventory records `EditAppFolderActivity` and `PickAppFolderActivity`. | High | All folder styling/behaviour. |
| E-006 | Inventory records `PickPageActivity`. | High | Exact page management options. |
| E-007 | Inventory records `PickImageActivity`, `PickTypefaceActivity`, `PickSoundActivity`, and `CropImageActivity`. | High | Complete image/font/sound editing features. |
| E-008 | Inventory records `PickSequenceActivity` and `EditSequenceActivity`. | High | The exact sequence step types, limits, and execution semantics. |
| E-009 | Inventory records `BackupManagementActivity`. | High | Exact backup schema, encryption, or cloud support. |
| E-010 | Inventory records `ApplyThemeActivity` and `SetWallpaperActivity`. | High | Every supported theme format or wallpaper mode. |
| E-011 | Inventory records `PickTaskerCommandActivity`, `EditSendTaskerVarActivity`, and a Tasker/Locale receiver. | High | Full integration API or security checks. |
| E-012 | Manifest inventory records a notification-listener service; DEX descriptor inventory includes `NotiCountView`. | High | Exact badge rules or how notification content is retained. |
| E-013 | DEX descriptor inventory includes `ContactPhotoView`. | High | Which contact fields are read and when. |
| E-014 | Billing activity/proxy components and `com.android.vending.BILLING` are present. | High | Product IDs, paid SKU mapping, entitlement checks, or which specific feature is premium. |
| E-015 | Native library inventory records GIF-related libraries across ABI splits. | High | Which app screens use animated images. |
| E-016 | Manifest permission inventory includes contacts, calendar, location, app visibility, notification access, accessibility, device-admin, wallpaper, Wi-Fi/Bluetooth, biometric, and billing-related declarations. | High | Runtime grants, permission use, or data transmission. |
| E-017 | DEX string inventory contains weather, geocoding, and RSS-feed URL strings. | High | Whether those endpoints are actually contacted. |
| E-018 | Extracted assets include a serialized `elements` data file and theme-picker references. | High | The private schema or exact meaning of the serialized element type codes. |

## 3. Reasonable product inferences

These are useful for planning but must not be represented as runtime-proven reference behaviour.

| Inference ID | Inference | Basis | Confidence |
|---|---|---|---|
| I-001 | The reference product is a full Home launcher rather than a simple app shortcut. | Home intent, workspace/root/page/window classes. | High |
| I-002 | The product likely supports multiple pages or page-like surfaces. | Page picker, view pager classes, page selection component. | High |
| I-003 | The product likely supports custom addable items and Android widgets. | Addable picker and widget picker components. | High |
| I-004 | Folders are a first-class editable object. | Folder picker and editor activities. | High |
| I-005 | Named panels/windows are a first-class feature family. | Window activity/layer/thumbnail/picker classes. | Medium-high |
| I-006 | Users can select images, fonts, sounds, and crop images as part of customization. | Dedicated selection/crop activities. | Medium-high |
| I-007 | The application has some configurable action/sequence concept. | Sequence picker and editor. | Medium |
| I-008 | Theme/icon-pack application is supported. | Theme activity, `ApplyThemeActivity` intent actions, theme-picker asset. | High |
| I-009 | Tasker/Locale-style external automation is supported. | Intent actions and receiver names. | High |
| I-010 | Notification-count and contact-photo elements may be supported. | Corresponding class descriptors and permission declarations. | Medium |
| I-011 | Weather and feed/news information elements may exist. | DEX URL constants and network strings. | Medium-low |

## 4. Additional resource-level audit (2026-11-02)

The APKM analysis bundle was unpacked in a temporary inspection directory and the existing `resource_inventory.csv`, `asset_inventory.csv`, `components.csv`, `permissions.csv`, `class_descriptor_inventory.txt`, and `dex_strings.txt` were reviewed again. The original APKM SHA-256 was independently recomputed in the current session and matched the report.

| Evidence ID | Direct observation | Confidence | Limit |
|---|---|---|---|
| E-019 | `MainActivity` has `ACTION_MAIN`, `ACTION_SEND`, and `ACTION_SEND_MULTIPLE` actions in `components.csv`. | High | Does not prove all MIME types are handled or how sharing works. |
| E-020 | Resource inventory contains `layout_media_controller.xml`, `layout_photo_slide.xml`, `layout_rss_reader.xml`, `layout_contacts.xml`, `l_lk_noti_panel.xml`, `layout_noti_panel_options.xml`, and `layout_toolbox.xml`. | High | Layout resource names do not prove each screen is reachable or fully functional. |
| E-021 | Resource inventory contains contact grid/list, RSS item, notification item, group/action/file/font/image/window item layouts. | High | Names alone do not establish all item properties or workflows. |
| E-022 | Resource inventory contains `option_pick_dynamic_image_activity_select_mode.xml`, `option_pick_shape_shadow_activity_select_mode.xml`, `layout_resizer.xml`, `layout_resizer_for_wnd.xml`, and `layout_rotator.xml`. | High | Indicates picker/editor resources; exact supported operations remain unknown. |
| E-023 | Drawable catalogue `customization/assets/drawable.xml` lists battery, CPU and RAM gauge backgrounds; notification badge; media transport; and Wi-Fi/Bluetooth/DND/ringer/hotspot/signal/brightness state artwork. | High | Artwork is not proof of a working status controller, metric source, or API permission. |
| E-024 | `customization/assets/init.zip` contains `pageList`, multiple page seed files, `pinBoard`, and shape presets `oval`, `round_rect`, and `round_rect_1`. | High | Seed asset names/content do not establish user-visible UI semantics. |
| E-025 | `customization/assets/elements` is a 14,769-byte serialized data asset containing element-like JSON records. | High | Its type codes/schema are private/unmapped; not decoded into OpenDeck requirements and must not be copied. |
| E-026 | `resource_inventory.csv` includes `activity_backup_center.xml`, `activity_pick_resource.xml`, `layout_resizer`, `layout_rotator`, and additional editor/picker layouts. | High | Does not establish exact backup operations or every editor feature. |
| E-027 | DEX descriptor inventory includes `com.ss.launcher2.key.IKeyService`, its Binder `Stub`/`Proxy` descriptors, and `com.ss.launcher2.key.C`. | High | Class names indicate a key-service IPC surface, but do not establish its purpose, entitlement logic, or feature gating. No key-service method was decompiled or invoked. |

### Additional implementation candidates added to OpenDeck

- Configurable media-control item using public `MediaSessionManager`/`MediaController` APIs, only when notification-listener access or another documented authorization path is available.
- User-selected photo-frame/slideshow and animated-image elements with off-screen pause and memory limits.
- Optional contacts grid/list, RSS/news reader, launcher-owned notification panel, and quick-action toolbox.
- Battery and available-memory indicators, with CPU gauge only if a supported non-privileged API exists; no hidden-file or hidden-API fallback.
- System-state indicators must not be mistaken for permission to toggle restricted device settings.
- Safe share-in support for allowed `ACTION_SEND`/`ACTION_SEND_MULTIPLE` file types with preview and explicit confirmation.
- Explicit grouping/ungrouping and optional depth effects in the workspace editor.

These are **OpenDeck product requirements or candidates**, not newly proven Total Launcher runtime features. `GAP_AUDIT.md` maps each finding to the updated PRD and records remaining questions.

## 5. Unknowns that remain open

1. Exact list of all item types and their editable properties.
2. Exact number, direction, and configuration of supported gestures.
3. Exact action-sequence grammar and error handling.
4. Exact panel/window presentation modes and lifecycle.
5. Exact page count/limits, grid limits, overlap semantics, and coordinate model.
6. Exact theme/import formats and compatibility rules.
7. Exact backup format, migration behaviour, and whether media is included.
8. Exact weather, RSS, calendar, contact, and badge settings/retention.
9. Exact premium SKU names and which individual capabilities are purchase-gated.
10. Exact accessibility-service and device-admin use cases.
11. Actual network requests, frequency, payloads, and runtime permissions.
12. Any features not discoverable from the selected manifest/class/string inventory.
13. Purpose and call flow of the `IKeyService` Binder interface; no entitlement conclusion can be drawn from its descriptor names alone.

## 6. Tools and coverage boundary

The current session does not expose the REA MCP analysis tools described by the installed REA companion skill. The supplied analysis bundle was reviewed; no new JADX method-level decompilation was performed during this pass. The available local shell also did not expose `jadx`, `apktool`, or Android `apkanalyzer` executables. Therefore:

- Do not call this a complete code-level reverse engineering of Total Launcher.
- No source methods or premium access-control code paths were reconstructed.
- No APK was installed or executed.
- No dynamic UI walkthrough, runtime permission observation, network capture, or instrumentation was performed.
- No original code, signing key, or app-private data was extracted.


## 7. OpenDeck implementation decisions derived from the evidence

1. Implement a Home role and normal app entry screen.
2. Build a custom multi-page workspace with freeform/grid layout and editor.
3. Implement addable item types, folders, widgets, panels/windows, and preview thumbnails.
4. Implement image/font/sound selection, dynamic images, photo slideshows, and resource pickers only through original OpenDeck formats/APIs.
5. Implement versioned action sequences and Tasker/Locale-style integration as an original documented contract.
6. Implement backup management, theme application, icon packs, and wallpaper/background selection.
7. Provide optional notification panel/badges, contacts grid/list, calendar, weather/RSS, media controls, photo slideshows, animated images, device gauges/status elements, and quick-action toolbox with minimum necessary permissions.
8. Remove billing/paywall from OpenDeck; all requested capabilities are available without purchase.
9. Do not reproduce proprietary assets, code, exact layouts, internal serialization formats, or branding.

## 8. Further validation required for a stronger parity claim

A legitimate, authorized, manual reference-app walkthrough should record each user-visible feature and observable setting, without modifying app entitlements or extracting protected user data. Capture the exact screen, entry path, user action, output, permission requirement, and any platform limitation. Add each observation to a feature matrix with a reproducible test. If method-level analysis is later performed with a configured, locally approved analysis tool, keep it limited to understanding normal feature behaviour; do not bypass licensing or premium enforcement.
