// Karma configuration
// Generated on Wed Feb 04 2015 16:27:05 GMT+0200 (EET)

module.exports = function (config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '../../',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],

    plugins: [
      'karma-jasmine',
      'karma-coverage',
      'karma-phantomjs-launcher',
      'karma-ng-html2js-preprocessor'
    ],


    // list of files / patterns to load in the browser
    files: [
      //css
      //'main/webapp/lib/menu/menu.css',
      'main/webapp/lib/bootstrap-3.3.2/css/bootstrap.min.css',
      //libs
      'main/webapp/lib/jquery/jquery.js',
      'main/webapp/lib/jquery/jquery.appear.js',
      'main/webapp/lib/angular/1.3.11/angular.js',
      'main/webapp/lib/angular/1.3.11/angular-sanitize.js',
      'main/webapp/lib/angular/ng-table/ng-table.js',
      'main/webapp/lib/highcharts/4.1.5/highcharts.js',
      'main/webapp/lib/angular/highcharts/highcharts-ng.js',
      'main/webapp/lib/angular-ui/0.13.0/ui-bootstrap-tpls.js',
      'main/webapp/lib/angular-ui/0.2.8/angular-ui-router.js',
      'main/webapp/lib/moment/moment.js',
      //app
      'main/webapp/scripts/*.js',
      'main/webapp/scripts/**/*.js',
      'main/webapp/scripts/**/**/*.js',
      //libs for test
      'test/test/lib/angular/angular-mocks.1.3.11.js',
      'test/test/lib/jasmine-2.2.0/jasmine.js',
      //tests
      'test/test/spec/**/*.js',
      'test/test/spec/**/**/*.js',
      //templates
      'main/webapp/views/**/*.html'
    ],


    // list of files to exclude
    exclude: [],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
      //'main/webapp/scripts/**/*.js': ['coverage'],
      'main/webapp/scripts/**/**/*.js': ['coverage'],
      'main/webapp/views/**/*.html': ['ng-html2js']
    },


    ngHtml2JsPreprocessor: {
      stripPrefix: 'main/webapp/'
      //moduleName: 'templates'
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress', 'coverage'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_DEBUG,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    //browsers: ['Chrome', 'Firefox'],
    //browsers: ['NodeWebkit'],
    browsers: ['PhantomJS2'],

    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // the default configuration
    junitReporter: {
      outputFile: 'test-results.xml',
      suite: ''
    },


    coverageReporter: {
      type: 'html',
      dir: 'coverage/'
    }
  });
};
