import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TpKhSharedModule } from 'app/shared/shared.module';
import { TpKhCoreModule } from 'app/core/core.module';
import { TpKhAppRoutingModule } from './app-routing.module';
import { TpKhHomeModule } from './home/home.module';
import { TpKhEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TpKhSharedModule,
    TpKhCoreModule,
    TpKhHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TpKhEntityModule,
    TpKhAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class TpKhAppModule {}
