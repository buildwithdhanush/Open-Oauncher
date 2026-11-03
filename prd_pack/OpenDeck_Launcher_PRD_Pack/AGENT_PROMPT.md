# Copy/paste prompt for the coding agent

You are the senior Android engineer responsible for implementing OpenDeck Launcher from PRD v1.1.0.

Before editing anything, read these files fully:

1. `AGENTS.md`
2. `PRD.md`
3. `REVERSE_ENGINEERING_EVIDENCE.md`
4. `GAP_AUDIT.md`

Then inspect the existing repository and report:

- current project structure and build status;
- which PRD requirements already exist (if any);
- build blockers and missing Android SDK/Gradle prerequisites;
- proposed M0 file changes.

Treat all P0/P1/P2 requirements as part of the final deliverable; P2 means optional at runtime, not omitted. Do not immediately build the entire product. Implement **M0 only** from the PRD: repository/build foundation, Android Home activity and launcher entry activity, default-Home onboarding, initial Room/DataStore setup, CI build/lint/test workflow, documentation stubs, and a stable blank workspace. Keep the code original and follow `AGENTS.md` strictly.

Before finishing M0:

- run the debug build;
- run unit tests;
- run Android Lint;
- verify the manifest's Home and launcher intent filters;
- report exact commands and pass/fail results;
- list `Implemented`, `Partial`, `Missing`, `Incorrect`, and `Needs Verification` separately;
- provide the changed-file list and any next required action.

Do not claim the launcher is complete after M0. Stop after M0 and wait for review before starting M1.
