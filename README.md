> [!NOTE]
> #### AI Assistance Disclaimer
> Some generic boilerplate (React / Spring scaffolding, DTO shells, config files) was
> generated with AI coding tools for efficiency. 
>
>**All architecture, business logic,
> problem decomposition, and implementation decisions — especially CSV parsing,
> flexible date handling, overlap calculations, and overall structure — are my own
> work and reflect my original analysis.**




# Employee-Pair Collaboration Analyzer 👥🔍

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-%236DB33F.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-61dafb.svg)](https://react.dev/)

> [!TIP]
> **Goal:** Upload a CSV of employee ↔ project assignments and instantly see  
> which **two employees have worked together the longest** on shared projects.

---

## 🗂 Repository layout

We use a monorepo–style structure to keep both frontend and backend code in a single repository for easy synchronization and unified issue tracking.
Each service lives in its own top‐level directory to **enforce separation of concerns:**

```

dimitrios-liasis-employees/
├─ backend/        # Spring Boot 3 (Java 17) – REST API & overlap logic
│  ├─ sample-data/    employees-sample1.csv (ISO)
│  │                 multi\_date\_format\_employees-sample2.csv (mixed formats)
│  └─ src/            Java source, DTOs, util/DateParser.java
└─ frontend/       # React 18 (Create-React-App) – file picker & results grid
├──|──public/          index.html
└──|──src/             components/, App.jsx, etc.

````

*Backend and frontend live in a single **monorepo** for easy cloning & CI.*

---

## ⚙️ Prerequisites

| Stack | Minimum version |
|-------|-----------------|
| **JDK** | 17 LTS |
| **Node JS** | 18 LTS |
| **Maven** | 3.8+ |

---

## 🚀 Run the app locally

> Terminal #1 – **API**

```bash
cd backend
mvn spring-boot:run
# ➜ API up on http://localhost:8080/api/upload
````

> Terminal #2 – **UI**

```bash
cd frontend
npm install         # first time only
npm start
# ➜ Opens http://localhost:3000 in your browser
```

The React app will proxy `POST /upload` to the Spring service thanks to
`axios.defaults.baseURL = 'http://localhost:8080/api'`.

---

## 📊 Try it with the bundled CSVs

| File                                                          | What it demonstrates                                              |
| ------------------------------------------------------------- | ----------------------------------------------------------------- |
| `backend/sample-data/employees-sample1.csv`                   | Straight ISO `yyyy-MM-dd` dates                                   |
| `backend/sample-data/multi_date_format_employees-sample2.csv` | Mixed formats (`yyyy-MM-dd`, `MM/dd/yyyy`, `dd.MM.yyyy` + `NULL`) |

1. Click **“Choose CSV”** in the UI.
2. Select a sample file.
3. See per-project overlaps in the grid and the **Top Pair** summary card.

---

## 🏗 Project structure in detail

### Backend (`backend/`)

| Layer          | Path                             | Highlights                                         |
| -------------- | -------------------------------- | -------------------------------------------------- |
| **Config**     | `config/WebConfig.java`          | CORS (localhost 3000) & size limit (5 MB)          |
| **Controller** | `controller/CsvController.java`  | `POST /api/upload`                                 |
| **Service**    | `service/CsvServiceImpl.java`    | Streams CSV, uses `util/DateParser` (multi-format) |
| **Algorithm**  | `service/OverlapCalculator.java` | Groups by project → pairs → aggregates totals      |
| **DTOs**       | `dto/`                           | Clean JSON contract (`records[]`, `topPair`)       |

### Frontend (`frontend/`)

| Folder                           | Purpose                               |
| -------------------------------- | ------------------------------------- |
| `src/components/FilePicker.jsx`  | Upload button + progress/error states |
| `src/components/ResultGrid.jsx`  | MUI **DataGrid** listing overlaps     |
| `src/components/SummaryCard.jsx` | Highlights the longest-working pair   |
| `src/index.js` / `App.jsx`       | Wire components & Axios base URL      |


---

## 📌 Notes & Decisions

* **Date formats supported**: `yyyy-MM-dd`, `MM/dd/yyyy`, `dd.MM.yyyy`, plus ISO variants.
* **`DateTo = NULL`** ⇒ treated as **today** on the server.
* **No database** – everything computed in memory per request for simplicity.
* React build uses **CRA/Webpack**; 


---

---

