
# Android only

[x] New Architect support

[x] Compatitive old Architect

# @vokhuyet/react-native-draw-overlay

Draw over other apps in Android

## Installation

```sh
npm install @vokhuyet/react-native-draw-overlay
```

## Extra

add to AndroidManifest.xml

```javascript
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" /> 
```

## Usage

```js
import { requestOverlayPermission, isOverlayPermissionGranted } from '@vokhuyet/react-native-draw-overlay';

// ...
const isGrantedBefore = await isOverlayPermissionGranted();

const grant = await requestOverlayPermission();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
