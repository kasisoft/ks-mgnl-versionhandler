Purpose
=======

Magnolia module providing an alternative solution in order to setup jcr configurations.


Requirements
------------

 * Java 8
 * Magnolia 5.4.9


Maven
-----

     <dependency>
         <groupId>com.kasisoft.mgnl</groupId>
         <artifactId>com.kasisoft.mgnl.versionhandler</artifactId>
         <version>0.1-SNAPSHOT</version>
     </dependency>
     
     <repositories>
         <repository>
             <id>kasisoft</id>
             <url>https://kasisoft.com/artifactory/remote-repos</url>
             <releases>
                 <enabled>true</enabled>
             </releases>
             <snapshots>
                 <enabled>true</enabled>
             </snapshots>
         </repository>
     </repositories>
     
     
Basic scenario
--------------

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
    
