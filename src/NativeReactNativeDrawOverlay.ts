import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  requestOverlayPermission(): Promise<boolean>;
  isOverlayPermissionGranted(): Promise<boolean>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('ReactNativeDrawOverlay');
