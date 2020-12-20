import { BrowserModule } from "@angular/platform-browser";
import { HttpClientModule } from "@angular/common/http"
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { TestComponent } from "./pages/test/test.component";
import { HeaderComponent } from "./components/header/header.component";
import { FooterComponent } from "./components/footer/footer.component";
import { UsersComponent } from "./pages/test/users/users.component";
import { LoginFormComponent } from "./components/login-form/login-form.component";
import { ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastrModule } from "ngx-toastr";
import { CookieService } from "ngx-cookie-service";
import { AppointmentsComponent } from './components/appointments/appointments.component';
import { FacilitiesComponent } from './pages/facilities/facilities.component';
import { FacilityComponent } from './pages/facility/facility.component';
import { DoctorComponent } from './pages/doctor/doctor.component';
import { HoursComponent } from './pages/hours/hours.component';

@NgModule({
    declarations: [
        AppComponent,
        TestComponent,
        HeaderComponent,
        FooterComponent,
        UsersComponent,
        LoginFormComponent,
        LoginFormComponent,
        AppointmentsComponent,
        FacilitiesComponent,
        FacilityComponent,
        DoctorComponent,
        HoursComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot({
            preventDuplicates: true,
        }),
        HttpClientModule,
        NgbModule,
    ],
    providers: [CookieService],
    bootstrap: [AppComponent],
})
export class AppModule {}
