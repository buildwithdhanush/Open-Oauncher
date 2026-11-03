# OpenDeck Launcher — PRD Pack v1.1.0

This folder is the AI coding-agent handoff for an independent Android launcher project.

## Files

- `PRD.md` — full product requirements, functional scope, architecture, platform constraints, security, acceptance criteria, milestones, and release gates.
- `REVERSE_ENGINEERING_EVIDENCE.md` — static evidence ledger for the supplied Total Launcher 4.1.5 APKM, with observations, inferences, unknowns, and analysis limits.
- `AGENTS.md` — implementation rules and step-by-step coding-agent protocol.
- `GAP_AUDIT.md` — v1.0.0 gap review and the evidence-driven additions made in v1.1.0.
- `CHANGELOG.md` — revision history.

**Audit status:** v1.1.0 includes a second static-resource inventory pass. It is not a full method-level or runtime reverse-engineering report; see `GAP_AUDIT.md` for limitations.

## Start here

1. Create a new Git repository for OpenDeck.
2. Extract the entire PRD pack into the repository or copy at least `AGENTS.md`, `PRD.md`, `REVERSE_ENGINEERING_EVIDENCE.md`, `GAP_AUDIT.md`, and `AGENT_PROMPT.md` into the repository root. Keep `CHANGELOG.md` and `README.md` as project documentation.
3. Give the coding agent the instruction: **“Read AGENTS.md, PRD.md, REVERSE_ENGINEERING_EVIDENCE.md, and GAP_AUDIT.md completely. Audit the repository, then implement M0 only. Do not skip tests or add unrequested dependencies.”**
4. Keep the build incremental. Do not ask an agent to generate the entire launcher in one patch.

## Project boundaries

OpenDeck is an independent project. It does not include Total Launcher code/assets, does not modify the reference APK, and does not implement any premium bypass. It intentionally has no paid tier: requested OpenDeck capabilities are available without billing.
