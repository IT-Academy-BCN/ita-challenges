@WebMvcTest(SubmissionController.class)
class SubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FinalizeSubmissionUseCase finalizeSubmissionUseCase;

    @MockBean
    private SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;

    @Test
    void shouldFinalizeSubmissionSuccessfully() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                "my solution"
        );

        doNothing().when(finalizeSubmissionUseCase).execute(any());

        mockMvc.perform(post("/api/challenge/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn409WhenSubmissionAlreadySubmitted() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                "my solution"
        );

        doThrow(new IllegalStateException("Submission already submitted"))
                .when(finalizeSubmissionUseCase).execute(any());

        mockMvc.perform(post("/api/challenge/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn404WhenSubmissionNotFound() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                "my solution"
        );

        doThrow(new IllegalArgumentException("Submission not found"))
                .when(finalizeSubmissionUseCase).execute(any());

        mockMvc.perform(post("/api/challenge/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
