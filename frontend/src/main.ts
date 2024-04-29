import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { RikModule } from './rik/rik.module';


platformBrowserDynamic().bootstrapModule(RikModule)
  .catch(err => console.error(err));
