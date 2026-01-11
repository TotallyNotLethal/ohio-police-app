# OHIO POLICE APP

A professional Android utility for Ohio peace officers that provides fast, offline access to the Ohio Revised Code (ORC), favorites, and an AI guidance assistant.

## Highlights
- Ohio Revised Code search across titles, chapters, sections, and text (offline-first).
- Statute detail view with favorite save/remove.
- AI guidance chat grounded in Ohio Revised Code context with explicit disclaimer.
- Local PIN gate stored with EncryptedSharedPreferences.
- Room persistence for favorites and chat history.
- Jetpack Compose UI with large touch targets for field use.

## Project Structure
```
app/
  src/main/
    assets/orc_ohio.json
    java/com/ohiopolice/app/
      data/
      network/
      ui/
      viewmodel/
    res/
```

## Ohio Revised Code Data
The app loads Ohio Revised Code statutes from `app/src/main/assets/orc_ohio.json`. The file currently includes 20 sample statutes. Replace or expand the JSON file with additional entries as needed.

Data model:
```json
{
  "title": "Title 29 - Crimes",
  "chapter": "2913",
  "section": "2913.02",
  "heading": "Theft",
  "body": "No person, with purpose to deprive the owner..."
}
```

## OpenAI Configuration (Manual Entry Required)
Set your OpenAI API key in `app/build.gradle.kts`:
```
buildConfigField("String", "OPENAI_API_KEY", "\"YOUR_OPENAI_API_KEY\"")
```

## Build & Run (Debug APK)
1. Open the project in Android Studio.
2. Sync Gradle.
3. Run on a device or emulator using the **Run** button.
4. For a debug APK: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Offline Behavior
- Ohio Revised Code search and favorites work fully offline.
- The AI assistant disables itself and warns when offline.

## Disclaimer
The AI guidance screen always shows:
"Guidance only. Ohio legal reference. Not official legal advice."

## Notes
- This app is Ohio-specific and does not include non-Ohio law.
- No analytics or PII collection is included.
