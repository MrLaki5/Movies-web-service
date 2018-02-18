
import db.MovieHelper;
import entities.Movie;
import java.io.ByteArrayInputStream;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ApplicationScoped
public class Image {

    @EJB
    public StreamedContent getImage(){
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            int idMovie=Integer.parseInt(id);
            Movie movie=new MovieHelper().getMovieById(idMovie);
            return new DefaultStreamedContent(new ByteArrayInputStream(movie.getPicture()));
        }
    }

}