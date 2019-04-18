# E-Stolowka

#Update angular 5.2 to 7.0
npm install -g @angular/cli
npm install @angular/cli
ng update @angular/cli
ng update @angular/core
ng update

npm install -g rxjs-tslint
rxjs-5-to-6-migrate -p src/tsconfig.app.json

ng update @angular/cli (again to change angular-cli.json to angular.json)

#additional angular dependencies
Required installations:
npm install --save @angular/material @angular/cdk @angular/animations

#installations required for authentication
npm install angular2-jwt
npm install
npm install --save rxjs-compat

#installation for scss
npm install bootstrap --save
npm install angular-star-rating css-star-rating --save