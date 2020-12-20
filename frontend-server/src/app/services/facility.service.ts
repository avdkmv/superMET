import { HttpClient } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { Urls } from "../constants/urls.enum"

@Injectable({
    providedIn: "root",
})
export class FacilityService {
    constructor(private http: HttpClient) {}

    public getAllFacilities() {
        return this.http.get(Urls.GET_ALL_FACILITIES)
    }

    public getDoctorsByFacility(id: string) {
        return this.http.get(Urls.GET_DOCTORS_BY_FACILITY.replace("{id}", id))
    }
}
