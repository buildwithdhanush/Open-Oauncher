# OpenDeck Launcher — Implementation Plan

## M0 Minimum Viable Foundation

### Goal
Establish a buildable Android launcher project with:
- Home activity and launcher entry with proper intent filters
- Default-Home onboarding flow
- Room database and DataStore persistence
- CI/build/lint/test workflow
- Documentation stubs
- Stable blank workspace

### Requirement IDs (P0/P1 from PRD)
- FR-HOME-001: Home activity declaration
- FR-HOME-002: Launcher entry declaration
- FR-HOME-003: First-launch onboarding
- FR-HOME-004: Load last saved workspace
- FR-HOME-005: Safe recovery on failure
- FR-HOME-006: Configuration changes handling
- FR-HOME-007: Package change broadcasts
- FR-APP-001: App discovery via package APIs
- FR-WS-001: Create/rename/reorder pages
- FR-WS-003: Page independent settings
- FR-WS-004: Grid mode configuration
- FR-ITEM-001: Add panel with item types
- FR-ITEM-002: Common item editor
- FR-ITEM-003: Item types (app, folder, widget, text, image, shape, spacer, custom action)
- FR-FOLDER-001: Create folders
- FR-FOLDER-002: Rename folders
- FR-BACKUP-001: Backup management create/list
- FR-BACKUP-002: Backup content specification
- FR-BACKUP-004: Versioned ZIP container format
- FR-SET-001: Searchable settings grouped by section

### Milestone Structure

| Milestone | Description | Target |
|---|---|---|
| **M0** | Repository/build foundation, Home activity, launcher entry, default-Home onboarding, initial Room/DataStore setup, CI/lint/tests, documentation stubs, blank workspace | Immediate |
| **M1** | App drawer, workspace pages, basic items, folders, edit mode | P0/P1 features |
| **M2** | Themes, icon packs, gestures, action sequences | P1 features |
| **M3** | Widgets, panels, media controls, system gauges | P1/P2 features |
| **M4** | Backup/restore, notifications, weather/calendar/contacts, RSS | P1/P2 features |
| **M5** | Full feature set, optional integrations, polish | Full PRD coverage |

### Initial M0 Deliverables
1. `IMPLEMENTATION_PLAN.md` — This file
2. `IMPLEMENTATION_STATUS.md` — Progress tracking
3. `BUILD_LOG.md` — Build command history and results
4. `TEST_REPORT.md` — Test execution results
5. Android Studio project with:
   - `build.gradle.kts` (Gradle Kotlin DSL)
   - `src/main/AndroidManifest.xml` with proper intent filters
   - `MainActivity.kt` — Home + launcher entry activity
   - `java/org/opendeck/launcher/` package structure
   - Room database skeleton
   - DataStore preferences skeleton
   - Empty workspace layout
6. Git repository with initial commit
7. CI workflow (GitHub Actions or equivalent)
8. Android Lint configuration

### Success Criteria for M0
- Project builds successfully with `./gradlew assembleDebug`
- Home activity declares `ACTION_MAIN + CATEGORY_HOME + CATEGORY_DEFAULT`
- Launcher entry declares `ACTION_MAIN + CATEGORY_LAUNCHER`
- No crashes on first launch
- Room database schema initialized (empty migration)
- DataStore preferences module created
- Lint passes with zero warnings
- Unit tests run and pass
- Onboarding flow shows "Choose as default Home" explanation
- Blank workspace loads on first launch