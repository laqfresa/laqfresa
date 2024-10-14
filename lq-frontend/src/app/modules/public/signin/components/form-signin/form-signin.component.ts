import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/services/auth.service';
import { NotificationService } from 'src/app/core/services/notification/notification.service';
import { FormPatterns } from 'src/app/core/utilities/utilities-interfaces';

@Component({
  selector: 'app-form-signin',
  templateUrl: './form-signin.component.html',
  styleUrls: ['./form-signin.component.css']
})
export class FormSigninComponent implements OnInit{

  validateForm !: FormGroup;
  formPattern = FormPatterns;
  passwordVisible: boolean = false;
  loadingLogin: boolean = false;

  numDocumentTouched: boolean = false;
  passwordTouched: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private notificationService : NotificationService
  ) {}

  ngOnInit(): void {
    this.validateForm = this.formBuilder.group({
      numDocument: ['',
      [
        Validators.required, 
        Validators.minLength(7), 
        Validators.maxLength(10), 
        Validators.pattern(this.formPattern._NUM_IDENTIFICACION_PATTERN)
      ]],
      password: ['', 
      [
        Validators.required, 
        Validators.minLength(6), 
        Validators.maxLength(15),
      ]]
    })
  }

  isValidForm(){
    if(this.validateForm.valid){
      this.signinWithIdentificationAndPassword();
    }else if(this.validateForm.invalid){
      this.showErrorTips();
    }
  }
 /**
  *   Realiza el inicio de sesion con el numero de documento y la contraseña
  */
  signinWithIdentificationAndPassword() {
    this.loadingLogin = true;
    const body = {
      identification: this.validateForm.controls['numDocument'].value,
      password: this.validateForm.controls['password'].value
    }
    this.authService.signinWhithIdentificationAndPassword(body.identification, body.password).subscribe((res) => {
      if(res) {
        this.router.navigate(['/']);
        this.loadingLogin = false;
        this.authService.saveRoleLogged(res.userType.name, res.firstName);
        this.authService.saveIdUser(res.documentNumber.toString())
        this.notificationService.success("Bienvenido a La Q'Fresa")
      }
    }, (error) => {
      let message: string = 'Ocurrio un error al iniciar sesión por favor intente nuevamente'
      if(error) {
        if(error && error.error && error.error.detail && error.status >= 400 || error.status < 499 ) {
          message = error.error.detail;
        }
      }
      this.loadingLogin = false;
      this.notificationService.error(message, 'Error');
    })
  }

  showErrorTips() {
    this.validateForm.controls['numDocument'].markAllAsTouched();
    this.validateForm.controls['numDocument'].updateValueAndValidity();
    this.validateForm.controls['password'].markAllAsTouched();
    this.validateForm.controls['password'].updateValueAndValidity();
  }

  /**
   * Marca como touched los campos del formulario para activar los error tips
   * @param controlName : Es el nombre del control del formulario
   */
  markTouched(controlName: string){
    if(controlName){
      this.validateForm.controls[controlName].markAllAsTouched();
      this.validateForm.controls[controlName].updateValueAndValidity();
    }
    if(controlName === 'numDocument'){
      this.numDocumentTouched = false;
    }else if(controlName === 'password') {
      this.passwordTouched = false;
    }
  }
} 
