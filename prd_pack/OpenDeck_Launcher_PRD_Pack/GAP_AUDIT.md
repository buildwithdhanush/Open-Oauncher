# OpenDeck PRD Gap Audit — v1.1.0

**Audit date:** 2026-11-02  
**Reference:** Total Launcher 4.1.5 (`com.ss.launcher2`) APKM, SHA-256 `b8ae5cba270019e9949783072e173d5145d0ae71714d86c04951281c56c42786`  
**Purpose:** Verify whether PRD v1.0.0 omitted capabilities indicated by the supplied static-analysis bundle, then update the product requirements.

## 1. Audit result

PRD v1.0.0 already covered the main launcher foundations: Home role, app drawer, multi-page/freeform workspace, folders, panels/windows, widgets, theme/icon-pack support, wallpapers, gestures/action sequences, Tasker/Locale, badges, weather/calendar/contacts basics, and backup/restore. The second pass found several missing or under-specified capability families. These are now added to PRD v1.1.0.

## 2. Newly added requirements

| Finding from supplied bundle | Evidence type | v1.1.0 requirement location | What was added | Confidence boundary |
|---|---|---|---|---|
| `layout_media_controller.xml` | Resource layout name | §3.14 FR-MEDIA-001..004 | Optional media session display and transport controls | Layout name suggests media UI; exact functionality unverified |
| `layout_photo_slide.xml` | Resource layout name | §3.4 FR-ITEM-012 | User-selected photo-frame/slideshow item | Resource name only |
| `layout_rss_reader.xml`, `item_rss*.xml`; RSS URLs in DEX strings | Layout/item names + strings | §3.13 FR-INFO-010 | Configurable HTTPS RSS reader | Strings/layouts do not prove fetch behaviour |
| `layout_contacts.xml`, `item_contact_grid.xml`, `item_contact_list.xml` | Resource layout/item names | §3.13 FR-INFO-009 | Contacts grid/list and quick-contact actions | Exact picker/data path unverified |
| `l_lk_noti_panel.xml`, `layout_noti_panel_options.xml`, `item_notification.xml`, `NotiListener` | Layout/item names + manifest component/class | §3.12 FR-NOTI-006..009 | Optional in-launcher notification panel, ephemeral content, user-driven actions | Service and layout observed; complete runtime behavior unverified |
| `layout_toolbox.xml` | Resource layout name | §3.4 FR-ITEM-013 | User-configurable quick-action toolbox | Resource name only |
| `ic_dynamic_image`, dynamic-image picker menu resource; GIF native library | Drawable/menu/native library inventory | §3.4 FR-ITEM-011 | Animated image item with memory/off-screen controls | Evidence suggests animated image support; exact formats/limits unknown |
| Battery/CPU/RAM gauge drawable names | Drawable catalogue | §3.14 FR-SYS-001..005 | Battery/memory gauges; CPU only with a safe documented API | Artwork does not prove metric computation |
| Wi-Fi/Bluetooth/DND/ringer/hotspot/signal/brightness artwork | Drawable catalogue | §3.14 FR-SYS-003..004 | Status indicators and safe links to system controls | Art does not prove toggle capability |
| `MainActivity` has `SEND` and `SEND_MULTIPLE` actions | Manifest component inventory | §3.2 FR-APP-011 | Safe share-in preview/import | Intent filters do not prove supported MIME types |
| `IKeyService` Binder interface descriptors | DEX class descriptor inventory | Evidence-only unknown; see E-027 | Added an explicit unresolved question; no entitlement implementation is inferred | Class names alone do not identify purpose or premium gates |
| `item_group.xml`, `tip_group_ungroup.xml`, resizer/rotator layouts | Resource names | §3.3 FR-WS-015; §3.4 FR-ITEM-013 | Explicit grouping/ungrouping and editing | Editor resource names; exact group semantics unknown |
| `init.zip` contains `pinBoard`, page seeds, and shape presets | Asset archive listing | Evidence-only; no copied data | Recorded as a candidate for manual walkthrough | Private schema/content not decoded |
| `ConfirmPinActivity`, standard shortcut actions in component inventory | Component/intent inventory | §3.11 FR-INT-006 | Supported user-confirmed Android shortcuts to panels/folders | Exact reference integration behavior unverified |

## 3. Existing requirements rechecked

| Area | v1.0.0 status | Audit disposition |
|---|---|---|
| Home/default launcher | Implemented in PRD | Keep |
| App drawer/search/sort/hide | Implemented in PRD | Keep; add safe share-in |
| Pages/freeform layout/layers | Implemented in PRD | Keep; add explicit groups and depth effects |
| Folders/panels/windows | Implemented in PRD | Keep; add external shortcut support |
| Android widgets | Implemented in PRD | Keep |
| Text/image/shape/font/sound pickers | Partially specified | Keep; add animated images/photo slideshow |
| Icon packs/themes/wallpaper | Implemented in PRD | Keep |
| Gestures/sequences/Tasker | Implemented in PRD | Keep |
| Notification support | Counts only | Expand to optional notification panel; privacy rules tightened |
| Weather/calendar/contact | Partially specified | Expand to contacts grid/list and RSS reader |
| Media controller | Missing | Add optional MediaSession-based integration |
| Device gauges/status tiles | Missing | Add only within documented API limits |
| Backup/restore | Implemented in PRD | Keep; include all new item settings/media metadata |

## 4. Corrections to the previous handoff

1. **Premium mapping:** The bundle contains billing components but no verified SKU-to-feature mapping. Do not infer which of the above features were premium in the reference. OpenDeck implements its own feature set for free; it does not patch the reference app.
2. **“All features” rule:** P2 means optional to enable, not optional to implement. The revised Definition of Done requires every P0/P1/P2 requirement before a full-capability-complete claim.
3. **Resource names are not behavior:** Several new items are only candidates inferred from resource names. They are now explicit OpenDeck requirements, but the evidence report does not mislabel them as runtime-proven reference features.
4. **Status controls:** Artwork for Wi-Fi/Bluetooth/DND/etc. does not grant permission to toggle those system states. The launcher must use public APIs and open system controls where direct changes are restricted.
5. **Media controls:** Third-party active media sessions need a valid access path. Android documents `MediaSessionManager.getActiveSessions()` for apps with an enabled notification listener; a normal third-party app must not assume privileged `MEDIA_CONTENT_CONTROL` permission.
6. **CPU gauge:** A drawable name is not proof that an app can read device CPU utilization through a public API. The PRD forbids hidden APIs and kernel-file scraping; unsupported devices must display “Unavailable”.
7. **Key service:** The DEX descriptor inventory names an `IKeyService` Binder interface. Its purpose and call flow are unverified; no premium-entitlement or purchase-check conclusion is made from that name.

## 5. Still missing evidence / unresolved items

- Method-level DEX decompilation: not performed. `jadx`, `apktool`, and the REA MCP toolset were not present in the current analysis environment.
- Runtime UI walkthrough: not performed; the APK was not installed or launched.
- Exact item catalogue and per-item property matrix: unknown.
- Exact page/window animation modes and limits: unknown.
- Exact sequence action grammar, error policy, and delays: unknown.
- Exact RSS provider list and polling policy: unknown.
- Exact notification panel actions, reply support, and retention: unknown.
- Exact media session selection/controls: unknown.
- Exact premium SKU mapping: unknown.
- Whether the pin-board seed is a user-visible feature or internal state: unknown.

## 6. Recommended next analysis step

The smallest high-value next step is a **manual, authorized feature walkthrough** of the installed reference version, recording each visible editor category, item type, gesture, panel, data source, and option. For method-level static analysis, configure a locally trusted JADX/JDK environment and inspect only normal feature behavior; do not modify billing or entitlement logic. Do not install or execute the untrusted APK on a primary device just for this audit.
