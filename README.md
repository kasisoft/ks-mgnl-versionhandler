# Purpose


This project provides a bunch of helpful tools in order to manage JCR configurations.


# Infos

* [eMail: daniel.kasmeroglu@kasisoft.com](mailto:daniel.kasmeroglu@kasisoft.com)
* [Issues](https://github.com/kasisoft/ks-mgnl-versionhandler/issues)
* [GIT](https://github.com/kasisoft/ks-mgnl-versionhandler)


# Development Setup

I assume that you're familiar with Maven. If not I suggest to visit the following page:

* https://maven.apache.org/


## Requirements

* Java 8
* Magnolia 5.6


## Maven

### Releases

     <dependency>
         <groupId>com.kasisoft.mgnl</groupId>
         <artifactId>ks-mgnl-versionhandler</artifactId>
         <version>0.7</version>
     </dependency>


### Snapshots

Snapshots can be used while accessing a dedicated maven repository. Your POM needs the following settings:

     <dependency>
         <groupId>com.kasisoft.mgnl</groupId>
         <artifactId>ks-mgnl-versionhandler</artifactId>
         <version>0.8-SNAPSHOT</version>
     </dependency>
     
     <repositories>
         <repository>
             <id>libs-kasisoft</id>
             <url>https://kasisoft.com/artifactory/libs-kasisoft</url>
             <releases>
                 <enabled>true</enabled>
             </releases>
             <snapshots>
                 <enabled>true</enabled>
             </snapshots>
         </repository>
     </repositories>
     

# Usage


    public CustomThemeVersionHandler() {
        register( 1, new VirtualUriMappingTask() );
        register( 1, "team-alpha", new AlphaMappingTask() );
        register( 2, new ErrorPageMappingTasks() );
    }
    
This causes the setup of the following properties within the module:

 * update_default     - if everything succeeds -> property will get the value 2
 * update_team-alpha  - if everything succeeds -> property will get the value 1


Tasks may depend on the *JcrConfigurationTask* which allows to register structures like this:

    public class TemplatesTask extends JcrConfigurationTask {
    
      public TemplatesTask() {
        super( "Templates installation", "install templates" );
        register( RepositoryConstants.CONFIG, newTemplatesTree() );
      }
      
      private TreeBuilder newTemplatesTree() {
        return new TreeBuilder()
          .sNode( "modules" )
            .sFolder( "templating" )
              .sFolder( "templates" )
                .sFolder( "pages" )
                  .sContentNode( "main" )
                    .yaml( "templating/templates/pages/main.yaml" )
                  .sEnd()
                .sEnd()
              .sEnd()
            .sEnd()
          .sEnd()
          ;
      }
    
    } /* ENDCLASS */

This task will grant node *main* below the path */modules/templating/templates/pages* within the *CONFIG* workspace. The
content of the node will be taken from the YAML resource *templating/templates/pages/main.yaml* which must be located on
the classpath (just an example). 
You can add properties directly, too.
A final sequence of *sEnd()* calls is allowed to be omitted, so the tree could be written like this as well:

    private TreeBuilder newTemplatesTree() {
      return new TreeBuilder()
        .sNode( "modules" )
          .sFolder( "templating" )
            .sFolder( "templates" )
              .sFolder( "pages" )
                .sContentNode( "main" )
                  .yaml( "templating/templates/pages/main.yaml" )
          ;
    }
    

# License

MIT License

Copyright (c) 2017 Daniel Kasmeroglu (Kasisoft)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
