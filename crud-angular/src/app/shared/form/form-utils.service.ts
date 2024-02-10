import { FormControl, FormGroup, UntypedFormArray, UntypedFormControl, UntypedFormGroup } from '@angular/forms';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  constructor() { }

  validateAllFormFields(formGroup : UntypedFormGroup | UntypedFormArray){
    Object.keys(formGroup.controls).forEach( field => {
      const control = formGroup.get(field);
      if(control instanceof UntypedFormControl){
        control.markAsTouched({onlySelf: true});
      } else if (control instanceof UntypedFormGroup || control instanceof UntypedFormArray) {
        control.markAsTouched({onlySelf: true});
        this.validateAllFormFields(control);
      }
    });
  }

  getErrorMessage(fieldName: string, FormGroup: UntypedFormGroup ) {
    const field = FormGroup.get(fieldName) as UntypedFormControl;
    return this.getErrorMessageFromField(field);
  }

  getErrorMessageFromField(field: UntypedFormControl) {

    if (field?.hasError('required')) {
      return 'Campo obrigatorio!';
    }

    if (field?.hasError('minlength')){
      const charsCount = field.errors ? field.errors['minlength']['requiredLength'] : 5 ;
      console.log(charsCount);
      return `Campo deve conter mais de ${charsCount} caracteres!`;
    }

    if (field?.hasError('maxlength')){
      const charsCount = field.errors ? field.errors['maxlength']['requiredLength'] : 200 ;
      return `Campo deve conter ate ${charsCount} caracteres!`;
    }

    return 'Campo obrigatorio!';
  }

  getFormArrayErrorMassage(formGroup: UntypedFormGroup, formArrayName: string, fieldName: string, index: number){
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    const field = formArray.controls[index].get(fieldName) as UntypedFormControl;
    return this.getErrorMessageFromField(field);
  }

  isFormArrayRequired(formGroup: UntypedFormGroup, formArrayName: string){
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    return !formArray.valid && formArray.hasError('required');
  }
}
